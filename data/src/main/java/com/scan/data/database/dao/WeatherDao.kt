package com.scan.data.database.dao

import android.graphics.Bitmap
import android.net.Uri
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

    @Query("SELECT * FROM " + WeatherResponse.TABLE_NAME + " WHERE imageBitmap =:bitmap")
    fun getCityWeatherData(bitmap: Bitmap): WeatherResponse

    @Query("UPDATE " + WeatherResponse.TABLE_NAME + " SET imageUri=:imageUri WHERE id = :id")
    fun insertImageUri(imageUri: Uri, id: Int)
}