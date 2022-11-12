package com.scan.weather.splash.presentation

import android.ptc.com.ptcflixing.base.view.UiState

sealed class SplashScreenUiState : UiState {

    object Loading : SplashScreenUiState()

    data class Error(val exception: RuntimeException) : SplashScreenUiState()

    object Success : SplashScreenUiState()


}