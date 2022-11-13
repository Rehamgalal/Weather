package com.scan.data.repository.weather

import android.graphics.Bitmap
import android.net.Uri
import com.scan.data.database.dao.WeatherDao
import com.scan.data.models.WeatherResponse

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getAllCitiesWeatherData(): List<WeatherResponse> {
        return weatherDao.getAllCitiesData()
    }

    override suspend fun saveCityWeatherData(weatherData: WeatherResponse, imageBitmap: Bitmap) {
        weatherData.imageBitmap = imageBitmap
        weatherDao.insertWeatherData(weatherData)
    }

    override suspend fun getLocalCityWeatherData(bitmap: Bitmap): WeatherResponse {
        return weatherDao.getCityWeatherData(bitmap)
    }

    override suspend fun insetImageUri(imageUri: Uri, id: Int) {
        weatherDao.insertImageUri(imageUri, id)
    }
}