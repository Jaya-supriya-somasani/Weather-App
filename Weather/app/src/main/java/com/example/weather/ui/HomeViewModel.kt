package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.retrofit.Result
import com.example.weather.retrofit.WeatherApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherApiHelper) : ViewModel() {
    fun getWeather() {
        viewModelScope.launch {
            when (val result = repository.getWeather()) {
                is Result.Success -> {
                    if (result.data != null) {
                        Log.d("Current-Weather", "---- Weather-data ---- ${result.data}")
                    } else {
                        Log.d("Current-Weather", "---- Weather-data is null")
                    }
                }

                is Result.Error -> {
                    Log.d("Current-Weather", "Failure-message ${result.exception.message}")
                }
            }
        }
    }

    fun getForeCastWeather() {
        viewModelScope.launch {
            when (val result = repository.getForeCastWeather()) {
                is Result.Success -> {
                    if (result.data != null) {
                        Log.d("Forecast-Weather", "--- Forecast-data--- ${result.data}")
                    } else {
                        Log.d("Forecast-Weather", "---- Forecast-data is null")
                    }
                }

                is Result.Error -> {
                    Log.d(
                        "Forecast-Weather",
                        "---Failure Forecast-message--- ${result.exception.message}"
                    )
                }
            }
        }
    }
}
