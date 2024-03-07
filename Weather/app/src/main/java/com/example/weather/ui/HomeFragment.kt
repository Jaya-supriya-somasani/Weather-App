package com.example.weather.ui

import android.animation.Animator
import android.animation.ObjectAnimator
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

    private fun startCardAnimation() {
        val cardView = binding.cardView
        val initialTranslationY = cardView.translationY
        cardView.translationY = initialTranslationY + 1000f
        val animator = ObjectAnimator.ofFloat(cardView, "translationY", initialTranslationY)
        animator.duration = 4000
        animator.start()
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
                if (it.isNotBlank()){
                    binding.tempTv.text = "${it}°"
                }
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

    private fun startAnimation() {
        binding.tempTv.visibility = View.INVISIBLE
        val animationView = binding.loadingAnimation
        animationView.setAnimation("loading_anim.json")
        animationView.playAnimation()

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                hideLoadingAnimation()
                viewModel.getWeather()
                viewModel.getForeCastWeather()
                startCardAnimation()
                binding.tempTv.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    private fun hideLoadingAnimation() {
        binding.loadingAnimation.visibility = View.GONE
    }

    private fun setUp() {
        binding.viewModel = viewModel
        binding.weekDayRecyclerView.adapter = adapter
        startAnimation()
    }
}