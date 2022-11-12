package com.scan.data.repository.authentication

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.AuthResponse
import kotlinx.coroutines.flow.Flow


interface AuthenticationRemoteDataSource {
    suspend fun getAuth(grantType: String, apiKey: String, apiSecret: String): AuthResponse
}

interface AuthenticationRepo {
    fun getAuth(grantType: String, apiKey: String, apiSecret: String): Flow<Resource<AuthResponse>>
    fun getAuthFromLocal(): Flow<Resource<AuthResponse>>

}

interface AuthenticationLocalDataSource {
    suspend fun saveAccessToken(authentication: AuthResponse)
    suspend fun getAccessToken(): AuthResponse?
}