package com.scan.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scan.data.models.WeatherResponse
import java.util.*

class Converters(
) {
    private val gson = Gson()

    @TypeConverter
    fun toCloudsJson(clouds: WeatherResponse.Clouds): String {
        return gson.toJson(clouds)
    }

    @TypeConverter
    fun fromCloudsJson(json: String): WeatherResponse.Clouds {
        val listType = object : TypeToken<WeatherResponse.Clouds?>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toCoordJson(coord: WeatherResponse.Coord): String {
        return gson.toJson(coord)
    }

    @TypeConverter
    fun fromCoordJson(json: String): WeatherResponse.Coord {
        val listType = object : TypeToken<WeatherResponse.Coord?>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toMainJson(main: WeatherResponse.Main): String {
        return gson.toJson(main)
    }

    @TypeConverter
    fun fromMainJson(json: String): WeatherResponse.Main {
        val listType = object : TypeToken<WeatherResponse.Main?>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toSysJson(sys: WeatherResponse.Sys): String {
        return gson.toJson(sys)
    }

    @TypeConverter
    fun fromSysJson(json: String): WeatherResponse.Sys {
        val listType = object : TypeToken<WeatherResponse.Sys?>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toWeatherJson(weather: List<WeatherResponse.Weather>): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun fromWeatherJson(json: String): List<WeatherResponse.Weather> {
        val listType = object : TypeToken<List<WeatherResponse.Weather?>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toWindJson(wind: WeatherResponse.Wind): String {
        return gson.toJson(wind)
    }

    @TypeConverter
    fun fromWindJson(json: String): WeatherResponse.Wind {
        val listType = object : TypeToken<WeatherResponse.Wind?>() {}.type
        return gson.fromJson(json, listType)
    }
}