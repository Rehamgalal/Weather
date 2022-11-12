package com.scan.weather.weather.view

import android.ptc.com.ptcflixing.base.view.BaseFragment
import com.scan.weather.R
import com.scan.weather.databinding.FragmentWeatherDetailsBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class WeatherDetailsFragment :
    BaseFragment<FragmentWeatherDetailsBinding, WeatherUiState, WeatherViewModel>() {
    override fun getVM(): WeatherViewModel = getSharedViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_weather_details

    override fun renderState(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.WeatherDetailRetrieved -> binding.weather =
                uiState.weatherDetailUiModel
            else -> Unit
        }
    }

}