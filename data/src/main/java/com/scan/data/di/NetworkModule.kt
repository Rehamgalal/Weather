package com.scan.data.di

import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.network.*
import com.scan.data.network.interceptors.DefaultInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun provideOkHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }

    fun provideOkHttpDefaultInterceptor(
        versionName: String,
        versionCode: Int,
        networkConnectivityHelper: NetworkConnectivityHelper,
    ) = DefaultInterceptor(
        versionName,
        versionCode,
        networkConnectivityHelper
    )


    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        defaultInterceptor: DefaultInterceptor,
        timeout: Long
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(defaultInterceptor)
            .build()
    }

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single(named(DI_WEATHER_BASE_URL)) { WEATHER_BASE_URL }

    single(named(DI_CITIES_BASE_URL)) { CITIES_BASE_URL }


    single { provideOkHttpLoggingInterceptor() }

    single {
        provideOkHttpDefaultInterceptor(
            get(named(DI_VERSION_NAME)), get(named(DI_VERSION_CODE)), get()
        )
    }

    single { (timeout: Long) -> provideOkHttpClient(get(), get(), timeout) }

    single(named(DI_RETROFIT_CITIES)) { (timeout: Long) ->
        provideRetrofit(
            get(named(DI_CITIES_BASE_URL)),
            get(parameters = { parametersOf(timeout) })
        )
    }
    single(named(DI_RETROFIT_WEATHER)) { (timeout: Long) ->
        provideRetrofit(
            get(named(DI_WEATHER_BASE_URL)),
            get(parameters = { parametersOf(timeout) })
        )
    }

    factory {
        get<Retrofit>(
            named(DI_RETROFIT_WEATHER),
            parameters = { parametersOf(DEFAULT_NETWORK_TIMEOUT_SECONDS) }
        ).create(WeatherApi::class.java)
    }
    factory {
        get<Retrofit>(
            named(DI_RETROFIT_CITIES),
            parameters = { parametersOf(DEFAULT_NETWORK_TIMEOUT_SECONDS) }
        ).create(CitiesApi::class.java)
    }

}

const val DI_WEATHER_BASE_URL = "WEATHER_BASE_URL"
const val DI_CITIES_BASE_URL = "CITIES_BASE_URL"
const val DI_RETROFIT_WEATHER = "RETROFIT_WEATHER "
const val DI_RETROFIT_CITIES = "RETROFIT_CITIES"
const val DI_VERSION_NAME = "VERSION_NAME"
const val DI_VERSION_CODE = "VERSION_CODE"