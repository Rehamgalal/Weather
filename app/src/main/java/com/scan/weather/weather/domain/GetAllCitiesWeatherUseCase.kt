package com.scan.weather.weather.domain

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.WeatherResponse
import com.scan.data.repository.weather.WeatherRepo
import kotlinx.coroutines.flow.Flow

class GetAllCitiesWeatherUseCase(private val weatherRepo: WeatherRepo) {

    operator fun invoke(): Flow<Resource<List<WeatherResponse>>> {
        return weatherRepo.getAllCitiesWeatherData()

    }
}