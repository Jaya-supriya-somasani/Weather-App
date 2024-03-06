package com.example.weather.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForeCaseWeather(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<WeatherDetails>,
    @SerializedName("city")
    val city: City
) : Parcelable

@Parcelize
data class WeatherDetails(
    @SerializedName("dt")
    val dt: Long,

    @SerializedName("main")
    val main: MainData,

    @SerializedName("weather")
    val weather: List<WeatherInfo>,

    @SerializedName("clouds")
    val clouds: CloudsCount,

    @SerializedName("wind")
    val wind: WindInfo,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("pop")
    val pop: Int,
    @SerializedName("sys")
    val sys: SysInfo,
    @SerializedName("dt_txt")
    val dateText: String
) : Parcelable

@Parcelize

data class MainData(
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("sea_level")
    val seaLevel: Int,

    @SerializedName("grnd_level")
    val grndLevel: Int,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("temp_kf")
    val tempKf: Double
) : Parcelable

@Parcelize
data class WeatherInfo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
) : Parcelable

@Parcelize
data class CloudsCount(
    @SerializedName("all")
    val all: Int
) : Parcelable

@Parcelize

data class WindInfo(
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val degree: Int,

    @SerializedName("gust")
    val gust: Double
) : Parcelable

@Parcelize
data class SysInfo(
    @SerializedName("pod")
    val pod: String
) : Parcelable

@Parcelize
data class City(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("coord")
    val coord: CoordInfo,

    @SerializedName("country")
    val country: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
) : Parcelable

@Parcelize
data class CoordInfo(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double
) : Parcelable

