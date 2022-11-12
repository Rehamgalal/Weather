package com.scan.weather.weather.view

import android.ptc.com.ptcflixing.base.view.BaseFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scan.weather.R
import com.scan.weather.databinding.FragmentSearchCitiesBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import com.scan.weather.weather.view.adapter.CityDataRecyclerAdapter
import com.scan.weather.weather.view.adapter.CityWeatherRecyclerAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class SearchCitiesFragment :
    BaseFragment<FragmentSearchCitiesBinding, WeatherUiState, WeatherViewModel>() {

    override fun onViewAttach() {
        super.onViewAttach()
        attachListener()

    }

    override fun getVM(): WeatherViewModel = getSharedViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_search_cities

    private fun attachListener() {
        binding.searchButton.setOnClickListener {
            viewModel.searchCities(binding.searchInput.text.toString())
        }
    }

    override fun renderState(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.CitySearchResult -> {
                uiState.citiesUiModel.let {
                    binding.recyclerView.adapter =
                        CityDataRecyclerAdapter(uiState.citiesUiModel) { long, lat ->
                            viewModel.getCityWeatherData(long, lat)
                        }
                }
            }
            is WeatherUiState.ErrorLocalCities -> binding.noCities.visibility = View.VISIBLE
            is WeatherUiState.WeatherDetailRequested -> findNavController().navigate(R.id.action_searchCitiesFragment_to_weatherDetailsFragment)
            else -> Unit
        }
    }


}