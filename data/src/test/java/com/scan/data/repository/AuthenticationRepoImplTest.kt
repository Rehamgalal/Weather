package com.scan.data.repository

import android.ptc.com.ptcflixing.base.data.Resource
import app.cash.turbine.test
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.MainCoroutineRule
import com.scan.data.models.AuthResponse
import com.scan.data.repository.authentication.AuthenticationLocalDataSource
import com.scan.data.repository.authentication.AuthenticationRemoteDataSource
import com.scan.data.repository.authentication.AuthenticationRepo
import com.scan.data.repository.authentication.AuthenticationRepoImpl
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthenticationRepoImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var remoteDataSource: AuthenticationRemoteDataSource

    @Mock
    private lateinit var localDataSource: AuthenticationLocalDataSource

    @Mock
    private lateinit var networkConnectivityHelper: NetworkConnectivityHelper

    @Mock
    private lateinit var authenticationRepo: AuthenticationRepo

    private var closeable: AutoCloseable? = null

    @Before
    fun beforeEach() {
        closeable = MockitoAnnotations.openMocks(this)

        whenever(networkConnectivityHelper.isConnected()).doReturn(true)

        authenticationRepo = Mockito.spy(
            AuthenticationRepoImpl(remoteDataSource, localDataSource, networkConnectivityHelper)
        )
    }

    @Test
    fun testGetAuthentication() = runTest {
        val result = mock<AuthResponse>()

        Mockito.`when`(remoteDataSource.getAuth(anyString(), anyString(), anyString()))
            .thenReturn(result)

        authenticationRepo.getAuth(anyString(), anyString(), anyString()).test {
            awaitItem() shouldBeInstanceOf Resource.Loading::class.java
            val successResult = awaitItem()
            successResult shouldBeInstanceOf Resource.Success::class.java
            (successResult as Resource.Success).data shouldBe result
            awaitComplete()
        }
    }

    @After
    @Throws(Exception::class)
    fun afterEach() {
        closeable?.close()

    }
}