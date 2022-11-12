package com.scan.data.network.interceptors

import com.scan.base.utils.NetworkConnectivityHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Default OkHttp interceptor used for adding headers, logging, etc.
 */
internal class DefaultInterceptor(
    private val versionName: String,
    private val versionCode: Int,
    private val networkConnectivityHelper: NetworkConnectivityHelper,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest: Request = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("version-name", versionName)
            .header("version-code", versionCode.toString())
            .header("device-type", "android")
        val request: Request = requestBuilder.build()

        try {
            val shouldCache = request.method == "GET"
            val maxAge = 30 //  get data stored 30 s ago from cache if online
            val maxStale =
                TimeUnit.DAYS.toSeconds(28) // get data stored 4 weeks ago data from cache if offline

            val response = chain.proceed(request).newBuilder()
                .also {
                    if (shouldCache) {
                        it.removeHeader("Cache-Control")
                        it.removeHeader("Pragma")
                        it.header(
                            "Cache-Control",
                            if (networkConnectivityHelper.isConnected()) "public, max-age=$maxAge" else "public, only-if-cached, max-stale=$maxStale"
                        )
                    }
                }
                .build()
            return response
        } catch (e: Exception) {
            throw e
        }
    }
}