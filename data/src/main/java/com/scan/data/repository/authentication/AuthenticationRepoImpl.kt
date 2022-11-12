package com.scan.data.repository.authentication

import android.ptc.com.ptcflixing.base.data.BaseRepo
import android.ptc.com.ptcflixing.base.data.DataNotFoundException
import android.ptc.com.ptcflixing.base.data.Resource
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.models.AuthResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRepoImpl(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepo(networkConnectivityHelper, ioDispatcher), AuthenticationRepo {
    override fun getAuth(
        grantType: String,
        apiKey: String,
        apiSecret: String
    ): Flow<Resource<AuthResponse>> {
        return networkOnlyFlowAndStore(remoteCall = {
            authenticationRemoteDataSource.getAuth(
                grantType,
                apiKey,
                apiSecret
            )
        }, localCall = {
            authenticationLocalDataSource.saveAccessToken(it)
        })
    }

    override fun getAuthFromLocal(): Flow<Resource<AuthResponse?>> {
        return localOnlyFlow { authenticationLocalDataSource.getAccessToken() }
    }
}