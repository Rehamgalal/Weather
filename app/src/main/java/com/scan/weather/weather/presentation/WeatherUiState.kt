package com.scan.weather.weather.presentation

import android.graphics.Bitmap
import android.ptc.com.ptcflixing.base.view.UiState

sealed class WeatherUiState : UiState {

    object Loading : WeatherUiState()

    object WeatherDetailRequested : WeatherUiState()

    object ErrorLocalCities : WeatherUiState()

    data class Error(val exception: RuntimeException) : WeatherUiState()

    data class WeatherDetailRetrieved(val weatherDetailUiModel: WeatherDetailUiModel) :
        WeatherUiState()

    data class AllCitiesRetrieved(val citiesUiModel: List<WeatherDetailUiModel>) :
        WeatherUiState()

    data class CitySearchResult(val citiesUiModel: List<CityUiModel>) : WeatherUiState()

    data class CityUiModel(
        val address: String,
        val long: Double,
        val lat: Double
    )

    data class WeatherDetailUiModel(
        val id: Int,
        val cityName: String,
        val currentTemp: String,
        val description: String,
        val feelsLike: String,
        val pressure: String,
        val humidity: String,
        val Wind: String,
        val minMax: String,
        val imageBitmap: Bitmap
    )
}