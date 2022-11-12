package com.scan.weather.weather.domain

import com.scan.data.models.WeatherResponse
import com.scan.data.repository.weather.WeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCityLocalWeatherDataUseCase(private val weatherRepo: WeatherRepo) {

    operator fun invoke(cityName: String): Flow<WeatherResponse> {
        return flow { weatherRepo.getLocalCityWeatherData(cityName) }
    }
}