package com.scan.data.repository.authentication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.scan.data.models.AuthResponse
import kotlinx.coroutines.flow.firstOrNull

class AuthenticationLocalDataSourceImpl(private val context: Context) :
    AuthenticationLocalDataSource {
    private val gson by lazy { Gson() }
    private val dataStoreName = "Weather_DataStore"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(dataStoreName)

    private val authenticationKey = stringPreferencesKey("authentication")
    override suspend fun saveAccessToken(authentication: AuthResponse) {
        val json: String = gson.toJson(authentication)
        context.dataStore.edit { preference -> preference[authenticationKey] = json }
    }

    override suspend fun getAccessToken(): AuthResponse? =
        context.dataStore.data.firstOrNull()?.let { preference ->
            gson.fromJson(preference[authenticationKey], AuthResponse::class.java)
        }
}
