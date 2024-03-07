package com.example.weather.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.math.round

@Parcelize
data class WeatherData(
    @SerializedName("coord")
    val coord: Coord,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: Main,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("clouds")
    val clouds: Clouds,

    @SerializedName("dt")
    val date: Long,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("cod")
    val cod: Int,
) : Parcelable

@Parcelize
data class Coord(
    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double
) : Parcelable

@Parcelize
data class Weather(
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
data class Main(
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

    @SerializedName("humidity")
    val humidity: Int
) : Parcelable{
    val celsius: Int
        get() = round(temp - 273.15).toInt()
}

@Parcelize
data class Wind(

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val degrees: Int
) : Parcelable

@Parcelize
data class Clouds(
    @SerializedName("all")
    val all: Int
) : Parcelable

@Parcelize
data class Sys(
    @SerializedName("type")
    val type: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
) : Parcelable

