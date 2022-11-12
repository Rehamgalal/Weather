package com.scan.data.repository.weather

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getWeatherData(lat: Double, lon: Double, apiKey: String): Flow<Resource<WeatherResponse>>
    fun getAllCitiesWeatherData(): Flow<Resource<List<WeatherResponse>>>
    fun getLocalCityWeatherData(cityName: String): Flow<Resource<WeatherResponse>>
}

interface WeatherRemoteDataSource {
    suspend fun getWeatherData(lat: Double, lon: Double, apiKey: String): WeatherResponse
}

interface WeatherLocalDataSource {
    suspend fun getAllCitiesWeatherData(): List<WeatherResponse>
    suspend fun saveCityWeatherData(weatherData: WeatherResponse)
    suspend fun getLocalCityWeatherData(cityName: String): WeatherResponse
}