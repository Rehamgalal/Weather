package com.scan.data.network

import com.scan.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather/")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<WeatherResponse>
}