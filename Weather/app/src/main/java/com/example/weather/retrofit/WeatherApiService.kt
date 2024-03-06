package com.example.weather.retrofit

import com.example.weather.data.ForeCaseWeather
import com.example.weather.data.WeatherData
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApiService {

    /**
     * This function gets the current location
     *
     */
    @GET("data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getSpecificWeather(): Response<WeatherData>


    /**
     * This function gets the forecast weather
     *
     */
    @GET("data/2.5/forecast?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getForeCastWeather(): Response<ForeCaseWeather>

}

