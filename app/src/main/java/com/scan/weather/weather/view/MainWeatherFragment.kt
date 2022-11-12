package com.scan.weather.weather.view

import android.ptc.com.ptcflixing.base.view.BaseFragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.scan.weather.R
import com.scan.weather.databinding.FragmentMainWeatherBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import com.scan.weather.weather.view.adapter.CityWeatherRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class MainWeatherFragment :
    BaseFragment<FragmentMainWeatherBinding, WeatherUiState, WeatherViewModel>() {
    override fun getVM(): WeatherViewModel = getSharedViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_main_weather
    override fun renderState(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.AllCitiesRetrieved -> {
                binding.recyclerView.adapter = CityWeatherRecyclerAdapter(uiState.citiesUiModel) {
                    viewModel.getCityWeather(it)
                }
            }
            is WeatherUiState.Error -> binding.noCities.visibility = View.VISIBLE
            is WeatherUiState.WeatherDetailRequested -> findNavController().navigate(R.id.action_mainWeatherFragment_to_weatherDetailsFragment)
            else -> Unit
        }
    }

}