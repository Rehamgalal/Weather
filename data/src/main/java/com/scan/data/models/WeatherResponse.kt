package com.scan.data.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    @PrimaryKey
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    var imageBitmap: Bitmap,
    var imageUri: Uri?
) : Parcelable {
    @Parcelize
    data class Clouds(
        val all: Int
    ) : Parcelable

    @Parcelize
    data class Coord(
        val lat: Double,
        val lon: Double
    ) : Parcelable

    @Parcelize
    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("grnd_level")
        val grndLevel: Int,
        val humidity: Int,
        val pressure: Int,
        @SerializedName("sea_level")
        val seaLevel: Int,
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
    ) : Parcelable

    @Parcelize
    data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    ) : Parcelable

    @Parcelize
    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ) : Parcelable

    @Parcelize
    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    ) : Parcelable

    companion object {
        @Ignore
        const val TABLE_NAME: String = "WeatherResponse"
    }
}