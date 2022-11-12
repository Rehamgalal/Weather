package com.scan.data.repository.search

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.CitiesResponse
import kotlinx.coroutines.flow.Flow

interface SearchCitiesRepo {
    fun searchCities(searchKey: String, accessToken: String): Flow<Resource<CitiesResponse>>
}

interface SearchCitiesRemoteDataSource {
    suspend fun searchCities(searchKey: String, accessToken: String): CitiesResponse
}