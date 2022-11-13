package com.scan.weather.weather.domain

import android.net.Uri
import com.scan.data.repository.weather.WeatherRepo

class InsertImageUriUseCase(private val weatherRepo: WeatherRepo) {

    operator fun invoke(imageUri: Uri, id: Int) {
        weatherRepo.insetImageUri(imageUri, id)
    }
}