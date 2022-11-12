package com.scan.data.repository.weather

import android.ptc.com.ptcflixing.base.data.BaseRepo
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper
) : BaseRepo(networkConnectivityHelper), WeatherRepo {
    override fun getWeatherData(lat: String, lon: String): Flow<Resource<WeatherResponse>> {
        return networkOnlyFlow { weatherRemoteDataSource.getWeatherData(lat, lon) }
    }
}