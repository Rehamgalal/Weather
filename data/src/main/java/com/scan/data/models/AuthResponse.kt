package com.scan.data.models

data class AuthResponse(
    val access_token: String,
    val application_name: String,
    val client_id: String,
    val expires_in: Int,
    val scope: String,
    val state: String,
    val token_type: String,
    val type: String,
    val username: String
)