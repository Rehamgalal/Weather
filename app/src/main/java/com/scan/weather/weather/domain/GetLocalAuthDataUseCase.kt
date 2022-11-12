package com.scan.weather.weather.domain

import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.data.models.AuthResponse
import com.scan.data.repository.authentication.AuthenticationRepo
import kotlinx.coroutines.flow.Flow

class GetLocalAuthDataUseCase(private val authenticationRepo: AuthenticationRepo) {

    operator fun invoke(): Flow<Resource<AuthResponse?>> {
        return authenticationRepo.getAuthFromLocal()
    }
}