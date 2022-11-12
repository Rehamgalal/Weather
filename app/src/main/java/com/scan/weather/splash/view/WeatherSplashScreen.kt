package com.scan.weather.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.ptc.com.ptcflixing.base.view.BaseActivity
import android.widget.Toast
import com.airbnb.lottie.LottieDrawable
import com.scan.weather.weather.view.WeatherActivity
import com.scan.weather.R
import com.scan.weather.databinding.ActivitySplashScreenBinding
import com.scan.weather.splash.presentation.SplashScreenUiState
import com.scan.weather.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

@SuppressLint("CustomSplashScreen")
class WeatherSplashScreen :
    BaseActivity<ActivitySplashScreenBinding, SplashScreenUiState, SplashViewModel>() {

    private fun startAnimation() {
        binding.lottieSplash.repeatCount = LottieDrawable.INFINITE
        binding.lottieSplash.playAnimation()
    }

    override fun getLayoutRes(): Int = R.layout.activity_splash_screen

    override fun getVM(): SplashViewModel = getViewModel()

    override fun renderState(it: SplashScreenUiState) {
        when (it) {
            is SplashScreenUiState.Loading -> {
                startAnimation()
            }
            is SplashScreenUiState.Error -> {
                Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
            }
            is SplashScreenUiState.Success -> {
                startActivity(Intent(this, WeatherActivity::class.java))
            }
        }
    }

    override fun getToolbarTitle(): Any? = null
}