package com.scan.data.di

import com.scan.base.utils.NetworkConnectivityHelper
import org.koin.dsl.module

val dataUtilsModule = module {

    single { NetworkConnectivityHelper() }
}