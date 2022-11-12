package com.scan.weather.weather.di

import com.scan.weather.weather.domain.*
import com.scan.weather.weather.presentation.WeatherMapper
import com.scan.weather.weather.presentation.WeatherViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val weatherModule = module {
    factoryOf(::GetLocalAuthDataUseCase)
    factoryOf(::GetWeatherDataUseCase)
    factoryOf(::SearchCitiesUseCase)
    factoryOf(::GetAllCitiesWeatherUseCase)
    factoryOf(::GetCityLocalWeatherDataUseCase)
    factoryOf(::WeatherMapper)
    factoryOf(::WeatherViewModel)

}