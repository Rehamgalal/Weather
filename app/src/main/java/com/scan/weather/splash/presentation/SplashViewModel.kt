package com.scan.weather.splash.presentation

import android.ptc.com.ptcflixing.base.data.Resource
import android.ptc.com.ptcflixing.base.view.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.scan.data.network.GRANT_TYPE
import com.scan.weather.BuildConfig.API_KEY
import com.scan.weather.BuildConfig.API_SECRET
import com.scan.weather.splash.domain.GetAuthenticationUseCase
import kotlinx.coroutines.launch

class SplashViewModel(private val getAuthenticationUseCase: GetAuthenticationUseCase) :
    BaseViewModel<SplashScreenUiState>(SplashScreenUiState.Loading) {

    init {
        getAuthentication()
    }

    fun getAuthentication() {
        val apiKey = API_KEY
        val apiSecret = API_SECRET
        val grantType = GRANT_TYPE
        viewModelScope.launch {
            getAuthenticationUseCase(grantType, apiKey, apiSecret).collect {
                when (it) {
                    is Resource.Loading -> updateState(SplashScreenUiState.Loading)
                    is Resource.Error -> updateState(SplashScreenUiState.Error(it.exception))
                    is Resource.Success -> updateState(SplashScreenUiState.Success)
                }
            }
        }
    }
}