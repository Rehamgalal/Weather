package com.scan.data.repository.weather

import com.scan.data.database.dao.WeatherDao
import com.scan.data.models.WeatherResponse

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getAllCitiesWeatherData(): List<WeatherResponse> {
        return weatherDao.getAllCitiesData()
    }

    override suspend fun saveCityWeatherData(weatherData: WeatherResponse) {
        weatherDao.insertWeatherData(weatherData)
    }

    override suspend fun getLocalCityWeatherData(cityName: String): WeatherResponse {
        return weatherDao.getCityWeatherData(cityName)
    }


}