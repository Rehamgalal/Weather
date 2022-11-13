package com.scan.weather.weather.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.ptc.com.ptcflixing.base.data.DataNotFoundException
import android.ptc.com.ptcflixing.base.data.Resource
import android.ptc.com.ptcflixing.base.view.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.scan.weather.BuildConfig.WEATHER_API_KEY
import com.scan.weather.weather.domain.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getAllCitiesWeatherUseCase: GetAllCitiesWeatherUseCase,
    private val getCityLocalWeatherDataUseCase: GetCityLocalWeatherDataUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val insertImageUriUseCase: InsertImageUriUseCase,
    private val weatherMapper: WeatherMapper
) : BaseViewModel<WeatherUiState>(WeatherUiState.Loading) {


    var imageBitmap: Bitmap? = null
    var imageUri: Uri? = null
    fun getAllCitiesWeatherData() {
        viewModelScope.launch {
            getAllCitiesWeatherUseCase().collect {
                when (it) {
                    is Resource.Loading -> updateState(WeatherUiState.Loading)
                    is Resource.Error -> updateState(WeatherUiState.Error(it.exception))
                    is Resource.Success ->
                        it.data?.let { list ->
                            if (list.isNotEmpty()) {
                                updateState(WeatherUiState.AllCitiesRetrieved(list.map { weatherResponse ->
                                    weatherMapper.toWeatherUiModel(
                                        weatherResponse
                                    )
                                }))

                            } else {
                                updateState(WeatherUiState.Error(DataNotFoundException()))

                            }
                        }
                }
            }
        }
    }

    fun getCityWeather(cityName: String) {
        updateState(WeatherUiState.WeatherDetailRequested)
        viewModelScope.launch {
            getAllCitiesWeatherUseCase().collect {
                when (it) {
                    is Resource.Loading -> updateState(WeatherUiState.Loading)
                    is Resource.Error -> updateState(WeatherUiState.Error(it.exception))
                    is Resource.Success ->
                        it.data?.let { list ->
                            val weatherDetails = list.findLast { it.name == cityName }
                            weatherDetails?.let {
                                imageUri = it.imageUri
                                updateState(
                                    WeatherUiState.WeatherDetailRetrieved(
                                        weatherMapper.toWeatherUiModel(
                                            it
                                        )
                                    )
                                )
                            }
                        }
                }
            }
        }
    }

    fun searchCities(searchQuery: String) {
        viewModelScope.launch {
            searchCitiesUseCase(searchQuery).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState(WeatherUiState.Loading)
                    is Resource.Error -> updateState(WeatherUiState.Error(resource.exception))
                    is Resource.Success ->
                        resource.data?.data?.let {
                            updateState(WeatherUiState.CitySearchResult(it.map {
                                weatherMapper.toCityUiModel(
                                    it
                                )
                            }))
                        }
                }
            }
        }
    }

    fun getCityWeatherData(long: Double, lat: Double) {
        val apiKey = WEATHER_API_KEY
        viewModelScope.launch {
            imageBitmap?.let {
                getWeatherDataUseCase(lat, long, apiKey, it).collectLatest {
                    when (it) {
                        is Resource.Loading -> updateState(WeatherUiState.WeatherDetailRequested)
                        is Resource.Error -> updateState(WeatherUiState.Error(it.exception))
                        is Resource.Success ->
                            it.data?.let {
                                imageUri = it.imageUri
                                updateState(
                                    WeatherUiState.WeatherDetailRetrieved(
                                        weatherMapper.toWeatherUiModel(
                                            it
                                        )
                                    )
                                )
                            }
                    }
                }
            }
        }
    }

    fun insertImageUri(imageUri: Uri?, id: Int) {
        imageUri?.let {
            insertImageUriUseCase(imageUri, id)
        }
    }
}