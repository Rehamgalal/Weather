package com.scan.weather.weather.view

import android.ptc.com.ptcflixing.base.view.BaseActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.scan.weather.R
import com.scan.weather.databinding.ActivityMainBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class WeatherActivity : BaseActivity<ActivityMainBinding, WeatherUiState, WeatherViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewAttach() {
        super.onViewAttach()
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.fab.setOnClickListener { view ->
            if (navController.currentDestination?.id == R.id.mainWeatherFragment) navController.navigate(
                R.id.action_mainWeatherFragment_to_searchCitiesFragment
            )
            else if (navController.currentDestination?.id == R.id.weatherDetailsFragment) navController.navigate(
                R.id.action_weatherDetailsFragment_to_searchCitiesFragment
            )
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getVM(): WeatherViewModel = getViewModel()

    override fun renderState(it: WeatherUiState) {

    }

    override fun getToolbarTitle(): Any? = null
}