package com.scan.weather

import android.app.Application
import com.scan.data.di.dataUtilsModule
import com.scan.data.di.networkModule
import com.scan.data.di.repositoryModule
import com.scan.weather.di.appVersionModule
import com.scan.weather.di.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoinDi()
    }

    private fun initKoinDi() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@WeatherApp)
            modules(
                listOf(
                    appVersionModule,
                    networkModule,
                    repositoryModule,
                    dataUtilsModule,
                    *featuresModule.toTypedArray()
                )
            )
        }
    }
}