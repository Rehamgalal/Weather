package com.scan.data.repository.authentication

import android.ptc.com.ptcflixing.base.data.BaseRemoteDataSource
import com.scan.data.models.AuthResponse
import com.scan.data.network.CitiesApi

class AuthenticationRemoteDataSourceImpl(private val citiesApi: CitiesApi) : BaseRemoteDataSource(),
    AuthenticationRemoteDataSource {
    override suspend fun getAuth(
        grantType: String,
        apiKey: String,
        apiSecret: String
    ): AuthResponse {
        return makeRequest { citiesApi.getAuth(grantType, apiKey, apiSecret) }
    }
}