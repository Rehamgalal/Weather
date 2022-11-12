package com.scan.data.repository.search

import android.ptc.com.ptcflixing.base.data.BaseRemoteDataSource
import com.scan.data.models.CitiesResponse
import com.scan.data.network.CitiesApi

class SearchCitiesRemoteDataSourceImpl(private val citiesApi: CitiesApi) : BaseRemoteDataSource(),
    SearchCitiesRemoteDataSource {
    override suspend fun searchCities(searchKey: String, accessToken: String): CitiesResponse {
        return makeRequest { citiesApi.searchCities(searchKey, accessToken) }
    }
}