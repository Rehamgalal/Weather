package com.scan.data.repository.authentication

import android.ptc.com.ptcflixing.base.data.BaseRepo
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.models.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRepoImpl(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper
) : BaseRepo(networkConnectivityHelper), AuthenticationRepo {
    override fun getAuth(
        grantType: String,
        apiKey: String,
        apiSecret: String
    ): Flow<Resource<AuthResponse>> {
        return flow {
            authenticationLocalDataSource.getAccessToken()?.let {
                emit(Resource.Success(it))
                return@flow
            }

            authenticationRemoteDataSource.getAuth(
                grantType,
                apiKey,
                apiSecret
            ).let {
                authenticationLocalDataSource.saveAccessToken(it)
            }
        }

    }
}