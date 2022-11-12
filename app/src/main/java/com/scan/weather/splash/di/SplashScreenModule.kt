package com.scan.weather.splash.di

import com.scan.weather.splash.domain.GetAuthenticationUseCase
import com.scan.weather.splash.presentation.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val splashScreenModule = module {
    factoryOf(::GetAuthenticationUseCase)
    factoryOf(::SplashViewModel)
}