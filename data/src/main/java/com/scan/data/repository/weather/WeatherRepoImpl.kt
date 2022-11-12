package com.scan.data.repository.weather

import android.ptc.com.ptcflixing.base.data.BaseRepo
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.models.WeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepo(networkConnectivityHelper, ioDispatcher), WeatherRepo {
    override fun getWeatherData(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Flow<Resource<WeatherResponse>> {
        return networkOnlyFlowAndStore(remoteCall = {
            weatherRemoteDataSource.getWeatherData(
                lat,
                lon,
                apiKey
            )
        },
            localCall = { localDataSource.saveCityWeatherData(it) })
    }

    override fun getAllCitiesWeatherData(): Flow<Resource<List<WeatherResponse>>> {
        return localOnlyFlow { localDataSource.getAllCitiesWeatherData() }
    }

    override fun getLocalCityWeatherData(cityName: String): Flow<Resource<WeatherResponse>> {
        return localOnlyFlow { localDataSource.getLocalCityWeatherData(cityName) }
    }
}