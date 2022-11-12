package com.scan.weather.weather.domain

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.WeatherResponse
import com.scan.data.repository.weather.WeatherRepo
import kotlinx.coroutines.flow.Flow

class GetWeatherDataUseCase(private val weatherRepo: WeatherRepo) {

    operator fun invoke(lat: Double, lon: Double, apiKey: String):
            Flow<Resource<WeatherResponse>> {
        return weatherRepo.getWeatherData(lat, lon, apiKey)
    }
}