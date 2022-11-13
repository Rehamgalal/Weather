package com.scan.weather.weather.view

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.ptc.com.ptcflixing.base.view.BaseFragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.scan.weather.R
import com.scan.weather.databinding.FragmentWeatherDetailsBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class WeatherDetailsFragment :
    BaseFragment<FragmentWeatherDetailsBinding, WeatherUiState, WeatherViewModel>() {
    override fun onViewAttach() {
        super.onViewAttach()
        attachListener()
    }

    private fun attachListener() {
        binding.shareButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    shareIntent(getImageUri())
                }
            }
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    private fun getImageUri(): Uri? {
        if (viewModel.imageUri != null) return viewModel.imageUri
        val bitmap = getBitmapFromView(binding.viewCapture)
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val imageUri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageInQ(bitmap, filename)
            else saveImageInLegacy(bitmap, filename)
        binding.weather?.let {
            viewModel.insertImageUri(imageUri, it.id)
        }
        return imageUri
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageInQ(bitmap: Bitmap?, filename: String): Uri? {
        val imageUri: Uri?
        var fos: OutputStream?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        //use application context to get contentResolver
        val contentResolver = requireContext().contentResolver

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use { bitmap?.compress(Bitmap.CompressFormat.JPEG, 70, it) }

        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        imageUri?.let { contentResolver.update(imageUri, contentValues, null, null) }
        return imageUri
    }

    private fun saveImageInLegacy(bitmap: Bitmap?, filename: String): Uri {
        val fos: OutputStream?
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        MediaScannerConnection.scanFile(
            requireContext(), arrayOf(filename),
            null, null
        )
        return FileProvider.getUriForFile(
            requireContext(), "${requireContext().packageName}.provider",
            File(filename)
        )
    }

    private fun shareIntent(uri: Uri?) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/*"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.putExtra(Intent.EXTRA_TEXT, "I found something cool!")
        requireContext().startActivity(Intent.createChooser(share, "Share Your Design!"))
    }

    override fun getVM(): WeatherViewModel = getSharedViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_weather_details

    override fun renderState(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.WeatherDetailRetrieved -> {
                binding.weather =
                    uiState.weatherDetailUiModel
            }

            else -> Unit
        }
    }

}