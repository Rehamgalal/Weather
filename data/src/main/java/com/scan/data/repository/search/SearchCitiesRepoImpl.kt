package com.scan.data.repository.search

import android.ptc.com.ptcflixing.base.data.BaseRepo
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.models.CitiesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class SearchCitiesRepoImpl(
    private val searchCitiesRemoteDataSource: SearchCitiesRemoteDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepo(networkConnectivityHelper, ioDispatcher), SearchCitiesRepo {
    override fun searchCities(
        searchKey: String,
        accessToken: String
    ): Flow<Resource<CitiesResponse>> {
        return networkOnlyFlow { searchCitiesRemoteDataSource.searchCities(searchKey, accessToken) }
    }
}