package com.scan.data.di

import com.scan.data.database.general.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    factory { AppDataBase.getInstance(androidContext()) }

    factory {
        val database = get<AppDataBase>()
        database.weatherDao()
    }

}