package com.scan.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scan.data.models.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM  " + WeatherResponse.TABLE_NAME)
    fun getAllCitiesData(): List<WeatherResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeatherData(weather: WeatherResponse)

    @Query("SELECT * FROM  " + WeatherResponse.TABLE_NAME + " WHERE name== :cityName ")
    fun getCityWeatherData(cityName: String): WeatherResponse
}