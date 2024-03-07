package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.City
import com.example.weather.data.Clouds
import com.example.weather.data.CloudsCount
import com.example.weather.data.Coord
import com.example.weather.data.CoordInfo
import com.example.weather.data.ForeCaseWeather
import com.example.weather.data.Main
import com.example.weather.data.MainData
import com.example.weather.data.Sys
import com.example.weather.data.SysInfo
import com.example.weather.data.Weather
import com.example.weather.data.WeatherData
import com.example.weather.data.WeatherDetails
import com.example.weather.data.WeatherInfo
import com.example.weather.data.Wind
import com.example.weather.data.WindInfo
import com.example.weather.retrofit.Result
import com.example.weather.retrofit.WeatherApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherApiHelper) : ViewModel() {

    val cityName = MutableStateFlow("")
    val tempInCelsius = MutableStateFlow("")
    var foreCastWeather = MutableStateFlow<ForeCaseWeather?>(
        null
    )
    private val currentWeather = MutableStateFlow<WeatherData?>(null)
    val isErrorTriggered = MutableStateFlow(false)

    fun getWeather() {
        viewModelScope.launch {
            when (val result = repository.getWeather()) {
                is Result.Success -> {
                    if (result.data != null) {
                        isErrorTriggered.value = false
                        currentWeather.value = WeatherData(
                            coord = Coord(
                                lon = result.data.coord.lon,
                                lat = result.data.coord.lat
                            ),
                            weather = listOf(
                                Weather(
                                    id = result.data.weather.firstOrNull()?.id ?: 0,
                                    main = result.data.weather.firstOrNull()?.main.orEmpty(),
                                    description = result.data.weather.firstOrNull()?.description.orEmpty(),
                                    icon = result.data.weather.firstOrNull()?.icon.orEmpty()
                                )
                            ),
                            base = result.data.base,
                            main = Main(
                                temp = result.data.main.temp,
                                feelsLike = result.data.main.feelsLike,
                                tempMin = result.data.main.tempMin,
                                tempMax = result.data.main.tempMax,
                                pressure = result.data.main.pressure,
                                humidity = result.data.main.humidity
                            ),
                            visibility = result.data.visibility,
                            wind = Wind(
                                speed = result.data.wind.speed,
                                degrees = result.data.wind.degrees
                            ),
                            clouds = Clouds(
                                all = result.data.clouds.all
                            ),
                            date = result.data.date,
                            sys = Sys(
                                type = result.data.sys.type,
                                id = result.data.sys.id,
                                country = result.data.sys.country,
                                sunrise = result.data.sys.sunrise,
                                sunset = result.data.sys.sunset
                            ),
                            timezone = result.data.timezone,
                            id = result.data.id,
                            name = result.data.name,
                            cod = result.data.cod
                        )

                        Log.d("Current-Weather", "---- Weather-data ---- ${currentWeather.value}")
                        cityName.value = result.data.name
                        tempInCelsius.value = result.data.main.celsius.toString()

                    } else {
                        Log.d("Current-Weather", "---- Weather-data is null")
                    }
                }

                is Result.Error -> {
                    isErrorTriggered.value = true
                    Log.d("Current-Weather", "Failure-message ${result.exception.message}")
                }

                else -> {}
            }
        }
    }

    fun getForeCastWeather() {
        viewModelScope.launch {
            when (val result = repository.getForeCastWeather()) {
                is Result.Success -> {
                    if (result.data != null) {
                        isErrorTriggered.value = false
                        foreCastWeather.value = ForeCaseWeather(
                            cod = result.data.cod,
                            message = result.data.message,
                            cnt = result.data.cnt,
                            city = City(
                                id = result.data.city.id,
                                name = result.data.city.name,
                                coord = CoordInfo(
                                    lat = result.data.city.coord.lat,
                                    lon = result.data.city.coord.lon
                                ),
                                country = result.data.city.country,
                                population = result.data.city.population,
                                timezone = result.data.city.timezone,
                                sunrise = result.data.city.sunrise,
                                sunset = result.data.city.sunset
                            ),
                            list = result.data.list.map { weatherDetails ->
                                WeatherDetails(
                                    dt = weatherDetails.dt,
                                    main = MainData(
                                        temp = weatherDetails.main.temp,
                                        feelsLike = weatherDetails.main.feelsLike,
                                        tempMin = weatherDetails.main.tempMin,
                                        tempMax = weatherDetails.main.tempMax,
                                        pressure = weatherDetails.main.pressure,
                                        seaLevel = weatherDetails.main.seaLevel,
                                        grndLevel = weatherDetails.main.grndLevel,
                                        humidity = weatherDetails.main.humidity,
                                        tempKf = weatherDetails.main.tempKf
                                    ),
                                    weather = weatherDetails.weather.map { weatherInfo ->
                                        WeatherInfo(
                                            id = weatherInfo.id,
                                            main = weatherInfo.main,
                                            description = weatherInfo.description,
                                            icon = weatherInfo.icon
                                        )
                                    },
                                    clouds = CloudsCount(all = weatherDetails.clouds.all),
                                    wind = WindInfo(
                                        speed = weatherDetails.wind.speed,
                                        degree = weatherDetails.wind.degree,
                                        gust = weatherDetails.wind.gust
                                    ),
                                    visibility = weatherDetails.visibility,
                                    pop = weatherDetails.pop,
                                    sys = SysInfo(pod = weatherDetails.sys.pod),
                                    dateText = weatherDetails.dateText
                                )
                            }
                        )

                        Log.d("Forecast-Weather", "--- Forecast-data--- ${foreCastWeather.value}")
                    } else {
                        Log.d("Forecast-Weather", "---- Forecast-data is null")
                    }
                }

                is Result.Error -> {
                    isErrorTriggered.value = true
                    Log.d(
                        "Forecast-Weather",
                        "---Failure Forecast-message--- ${result.exception.message}"
                    )
                }

                else -> {}
            }
        }
    }
}
