package com.scan.data.di

import com.scan.data.repository.authentication.AuthenticationLocalDataSource
import com.scan.data.repository.authentication.AuthenticationLocalDataSourceImpl
import com.scan.data.repository.authentication.AuthenticationRemoteDataSource
import com.scan.data.repository.authentication.AuthenticationRemoteDataSourceImpl
import com.scan.data.repository.authentication.AuthenticationRepo
import com.scan.data.repository.authentication.AuthenticationRepoImpl
import com.scan.data.repository.search.SearchCitiesRemoteDataSource
import com.scan.data.repository.search.SearchCitiesRemoteDataSourceImpl
import com.scan.data.repository.search.SearchCitiesRepo
import com.scan.data.repository.search.SearchCitiesRepoImpl
import com.scan.data.repository.weather.WeatherRemoteDataSource
import com.scan.data.repository.weather.WeatherRepo
import com.scan.data.repository.weather.WeatherRepoImpl
import com.scan.data.repository.weather.WeatherRemoteDataSourceImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    // Authentication Repo
    factoryOf(::AuthenticationRemoteDataSourceImpl) bind AuthenticationRemoteDataSource::class
    factoryOf(::AuthenticationLocalDataSourceImpl) bind AuthenticationLocalDataSource::class
    factory<AuthenticationRepo> { AuthenticationRepoImpl(get(), get(), get()) }

    // Search Repo
    factoryOf(::SearchCitiesRemoteDataSourceImpl) bind SearchCitiesRemoteDataSource::class
    factory<SearchCitiesRepo> { SearchCitiesRepoImpl(get(), get()) }

    // Weather Repo
    factoryOf(::WeatherRemoteDataSourceImpl) bind WeatherRemoteDataSource::class
    factory<WeatherRepo> { WeatherRepoImpl(get(), get()) }

}