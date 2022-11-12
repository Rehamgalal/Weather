package com.scan.weather.di

import com.scan.data.di.DI_VERSION_CODE
import com.scan.data.di.DI_VERSION_NAME
import com.scan.weather.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for app version name & code.
 */
val appVersionModule = module {

    single(named(DI_VERSION_NAME)) {
        BuildConfig.VERSION_NAME
    }

    single(named(DI_VERSION_CODE)) {
        BuildConfig.VERSION_CODE
    }

}