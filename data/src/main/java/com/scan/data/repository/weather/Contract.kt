package com.scan.data.repository.weather

import android.graphics.Bitmap
import android.net.Uri
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getWeatherData(
        lat: Double,
        lon: Double,
        apiKey: String,
        imageBitmap: Bitmap
    ): Flow<Resource<WeatherResponse>>

    fun getAllCitiesWeatherData(): Flow<Resource<List<WeatherResponse>>>
    fun getLocalCityWeatherData(bitmap: Bitmap): Flow<Resource<WeatherResponse>>
    fun insetImageUri(imageUri: Uri, id: Int)

}

interface WeatherRemoteDataSource {
    suspend fun getWeatherData(lat: Double, lon: Double, apiKey: String): WeatherResponse
}

interface WeatherLocalDataSource {
    suspend fun getAllCitiesWeatherData(): List<WeatherResponse>
    suspend fun saveCityWeatherData(weatherData: WeatherResponse, imageBitmap: Bitmap)
    suspend fun getLocalCityWeatherData(bitmap: Bitmap): WeatherResponse
    suspend fun insetImageUri(imageUri: Uri, id: Int)
}