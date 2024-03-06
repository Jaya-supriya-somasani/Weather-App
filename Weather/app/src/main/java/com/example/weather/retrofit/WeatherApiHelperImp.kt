package com.example.weather.retrofit

import com.example.weather.data.ForeCaseWeather
import com.example.weather.data.WeatherData
import javax.inject.Inject

class WeatherApiHelperImp @Inject constructor(private val apiService: WeatherApiService) :
    WeatherApiHelper {

    override suspend fun getWeather(): Result<WeatherData> = try {
        val response = apiService.getSpecificWeather()
        if (response.isSuccessful) {
            Result.Success(response.body())
        } else {
            Result.Error(Exception(response.message()))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getForeCastWeather(): Result<ForeCaseWeather> = try {
        val foreCaseWeatherResponse = apiService.getForeCastWeather()
        if (foreCaseWeatherResponse.isSuccessful) {
            Result.Success(foreCaseWeatherResponse.body())
        } else {
            Result.Error(Exception((foreCaseWeatherResponse.message())))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}


