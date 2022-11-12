package com.scan.weather.di

import com.scan.weather.splash.di.splashScreenModule
import com.scan.weather.weather.di.weatherModule

val featuresModule = listOf(splashScreenModule, weatherModule)