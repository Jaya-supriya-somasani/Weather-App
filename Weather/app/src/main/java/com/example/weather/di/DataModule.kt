package com.example.weather.di

import com.example.weather.retrofit.WeatherApiHelper
import com.example.weather.retrofit.WeatherApiHelperImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {
    @Binds
    abstract fun bindRemoteDataSource(weatherApiHelperImp: WeatherApiHelperImp): WeatherApiHelper
}