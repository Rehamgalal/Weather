package com.scan.data.repository.weather

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getWeatherData(lat: String, lon: String): Flow<Resource<WeatherResponse>>
}

interface WeatherRemoteDataSource {
    suspend fun getWeatherData(lat: String, lon: String): WeatherResponse
}