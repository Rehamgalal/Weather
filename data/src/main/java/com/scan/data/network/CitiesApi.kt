package com.scan.data.network

import com.scan.data.models.AuthResponse
import com.scan.data.models.CitiesResponse
import retrofit2.Response
import retrofit2.http.*

interface CitiesApi {

    @FormUrlEncoded
    @POST("/v1/security/oauth2/token")
    suspend fun getAuth(
        @Field("grant_type") grantType: String,
        @Field("client_id") apiKey: String,
        @Field("client_secret") apiSecret: String
    ): Response<AuthResponse>

    @GET("/v1/reference-data/locations/cities/")
    suspend fun searchCities(
        @Header("Authorization") accessToken: String,
        @Query("keyword") searchKey: String
    ): Response<CitiesResponse>
}