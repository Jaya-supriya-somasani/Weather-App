package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStateAtLeast
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.ui.adapter.WeeksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: WeeksAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        initObservers(viewLifecycleOwner)
    }


    private fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        viewLifecycleOwner.safeLaunchWhenResumed {
            viewModel.cityName.collectLatest {
                binding.locationTv.text = it
            }
        }
        viewLifecycleOwner.safeLaunchWhenResumed {
            viewModel.foreCastWeather.collect { foreCastWeather ->
                foreCastWeather?.let {
                    adapter.submitList(it.list)
                }
            }
        }

    }


    private fun setUp() {
        binding.viewModel = viewModel
        viewModel.getWeather()
        viewModel.getForeCastWeather()
        binding.weekDayRecyclerView.adapter=adapter
    }
}


fun LifecycleOwner.safeLaunchWhenResumed(
    showToastOnError: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
) = safeLaunch {
    lifecycle.whenStateAtLeast(Lifecycle.State.RESUMED) {
        try {
            block()
        } catch (e: Exception) {
            if (showToastOnError) {
                Log.d("error", e.toString())
            }
            throw e
        }
    }
}

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    if (throwable is CancellationException) return@CoroutineExceptionHandler
    throwable.printStackTrace()
    Log.d("throwable", "coroutineExceptionHandler")
}

fun LifecycleOwner.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    showToastOnError: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.safeLaunch(context, start) {
        try {
            block()
        } catch (e: Exception) {
            Log.d("Exception", "Exception $e")
            throw e
        }
    }
}

fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context + coroutineExceptionHandler, start, block)
}