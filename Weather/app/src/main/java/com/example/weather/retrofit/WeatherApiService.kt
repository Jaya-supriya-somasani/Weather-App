package com.example.weather.retrofit

import com.example.weather.data.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    /**
     * This function gets the [NetworkWeather] for the current [location]
     *
     */
    @GET("data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getSpecificWeather(): Response<WeatherData>

}

