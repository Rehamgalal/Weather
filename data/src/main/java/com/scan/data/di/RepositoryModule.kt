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
import com.scan.data.repository.weather.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    // Authentication Repo
    factoryOf(::AuthenticationRemoteDataSourceImpl) bind AuthenticationRemoteDataSource::class
    single<AuthenticationLocalDataSource> { AuthenticationLocalDataSourceImpl(androidContext()) }
    factory<AuthenticationRepo> { AuthenticationRepoImpl(get(), get(), get()) }

    // Search Repo
    factoryOf(::SearchCitiesRemoteDataSourceImpl) bind SearchCitiesRemoteDataSource::class
    factory<SearchCitiesRepo> { SearchCitiesRepoImpl(get(), get()) }

    // Weather Repo
    factoryOf(::WeatherRemoteDataSourceImpl) bind WeatherRemoteDataSource::class
    factoryOf(::WeatherLocalDataSourceImpl) bind WeatherLocalDataSource::class
    factory<WeatherRepo> { WeatherRepoImpl(get(), get(), get()) }

}