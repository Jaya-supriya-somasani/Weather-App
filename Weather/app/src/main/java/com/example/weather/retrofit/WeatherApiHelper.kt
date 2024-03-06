package com.example.weather.retrofit

import com.example.weather.data.ForeCaseWeather
import com.example.weather.data.WeatherData

interface WeatherApiHelper {
    suspend fun getWeather(): Result<WeatherData>

    suspend fun getForeCastWeather(): Result<ForeCaseWeather>
}
