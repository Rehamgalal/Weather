package com.scan.weather.weather.presentation

import com.scan.data.models.CitiesResponse
import com.scan.data.models.WeatherResponse

class WeatherMapper {

    fun toCityCardUiModel(weatherResponse: WeatherResponse): WeatherUiState.CityWeatherCardUiModel {
        return WeatherUiState.CityWeatherCardUiModel(
            weatherResponse.id,
            weatherResponse.name,
            "${weatherResponse.main.temp} \u2109",
            weatherResponse.weather.first().description,
            "H:${weatherResponse.main.tempMax} \u2109",
            "L:${weatherResponse.main.tempMin} \u2109"
        )
    }

    fun toCityUiModel(cityData: CitiesResponse.Data): WeatherUiState.CityUiModel {
        return WeatherUiState.CityUiModel(
            "${cityData.name}, ${cityData.address.countryCode}",
            cityData.geoCode.longitude,
            cityData.geoCode.latitude
        )
    }

    fun toWeatherUiModel(weatherResponse: WeatherResponse): WeatherUiState.WeatherDetailUiModel {
        return WeatherUiState.WeatherDetailUiModel(
            weatherResponse.name,
            "${weatherResponse.main.temp} \u2109",
            weatherResponse.weather.first().description,
            "Feels like:${weatherResponse.main.feelsLike} ℉",
            "Pressure:${weatherResponse.main.pressure}",
            "Humidity:${weatherResponse.main.humidity}",
            "WindSpeed:${weatherResponse.wind.speed}, WindDegree:${weatherResponse.wind.deg}",
            "Max:${weatherResponse.main.tempMax} \u2109, Min:${weatherResponse.main.tempMin} \u2109"
        )
    }
}