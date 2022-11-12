package com.scan.weather.splash.domain

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.AuthResponse
import com.scan.data.repository.authentication.AuthenticationRepo
import kotlinx.coroutines.flow.Flow

class GetAuthenticationUseCase(private val authenticationRepo: AuthenticationRepo) {

    operator fun invoke(
        grantType: String,
        apiKey: String,
        apiSecret: String
    ): Flow<Resource<AuthResponse>> {
        return authenticationRepo.getAuth(grantType, apiKey, apiSecret)
    }
}