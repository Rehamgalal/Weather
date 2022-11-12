package com.scan.data.repository.weather

import android.ptc.com.ptcflixing.base.data.BaseRemoteDataSource
import com.scan.data.models.WeatherResponse
import com.scan.data.network.WeatherApi

class WeatherRemoteDataSourceImpl(private val weatherApi: WeatherApi) : BaseRemoteDataSource(),
    WeatherRemoteDataSource {
    override suspend fun getWeatherData(lat: String, lon: String): WeatherResponse {
        return makeRequest { weatherApi.getWeatherData(lat, lon) }
    }
}