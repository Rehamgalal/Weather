package com.scan.data.repository

import android.ptc.com.ptcflixing.base.data.Resource
import app.cash.turbine.test
import com.scan.base.utils.NetworkConnectivityHelper
import com.scan.data.MainCoroutineRule
import com.scan.data.models.WeatherResponse
import com.scan.data.repository.weather.WeatherLocalDataSource
import com.scan.data.repository.weather.WeatherRemoteDataSource
import com.scan.data.repository.weather.WeatherRepo
import com.scan.data.repository.weather.WeatherRepoImpl
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class WeatherRepoImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @Mock
    private lateinit var localDataSource: WeatherLocalDataSource


    @Mock
    private lateinit var networkConnectivityHelper: NetworkConnectivityHelper

    @Mock
    private lateinit var weatherRepo: WeatherRepo

    private var closeable: AutoCloseable? = null

    @Before
    fun beforeEach() {
        closeable = MockitoAnnotations.openMocks(this)

        whenever(networkConnectivityHelper.isConnected()).doReturn(true)

        weatherRepo = Mockito.spy(
            WeatherRepoImpl(remoteDataSource, localDataSource, networkConnectivityHelper)
        )
    }

    @Test
    fun testGetAuthentication() = runTest {
        val result = mock<WeatherResponse>()
        Mockito.`when`(
            remoteDataSource.getWeatherData(
                ArgumentMatchers.anyDouble(),
                ArgumentMatchers.anyDouble(),
                anyString()
            )
        )
            .thenReturn(result)

        weatherRepo.getWeatherData(
            ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble(),
            anyString()
        )
            .test {
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