package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.ui.adapter.WeeksAdapter
import com.example.weather.utils.safeLaunchWhenResumed
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

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

        viewLifecycleOwner.safeLaunchWhenResumed {
            viewModel.tempInCelsius.collectLatest {
                binding.tempTv.text = "${it}°"
            }
        }
        viewLifecycleOwner.safeLaunchWhenResumed {
            viewModel.isErrorTriggered.collectLatest { isError ->
                if (isError) {
                    showSnackBar()
                    viewModel.isErrorTriggered.value = false
                }
            }
        }
    }

    private fun showSnackBar() {
        this.view?.let {
            Snackbar.make(it, "Something went wrong", Snackbar.LENGTH_LONG)
                .setAction("RETRY") {
                    viewModel.getWeather()
                    viewModel.getForeCastWeather()
                }
                .show()
        }
    }

    private fun setUp() {
        binding.viewModel = viewModel
        viewModel.getWeather()
        viewModel.getForeCastWeather()
        binding.weekDayRecyclerView.adapter = adapter
    }
}