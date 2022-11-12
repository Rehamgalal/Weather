package com.scan.weather.weather.domain

import android.ptc.com.ptcflixing.base.data.DataNotFoundException
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.CitiesResponse
import com.scan.data.repository.search.SearchCitiesRepo
import kotlinx.coroutines.flow.*

class SearchCitiesUseCase(
    private val citiesRepo: SearchCitiesRepo,
    private val getLocalAuthDataUseCase: GetLocalAuthDataUseCase
) {

    operator fun invoke(searchKey: String): Flow<Resource<CitiesResponse>> {
        return channelFlow {
            getLocalAuthDataUseCase().collectLatest {
                when (it) {
                    is Resource.Success -> it.data?.access_token?.let { accessToken ->
                        send(
                            citiesRepo.searchCities(
                                searchKey,
                                accessToken
                            ).last()
                        )
                    }
                    is Resource.Error -> {
                        send(Resource.Error(DataNotFoundException()))
                    }
                    Resource.Loading -> Unit
                }
            }
        }
    }
}