package com.scan.weather.weather.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.ptc.com.ptcflixing.base.view.BaseFragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import com.scan.weather.R
import com.scan.weather.databinding.FragmentSearchCitiesBinding
import com.scan.weather.weather.presentation.WeatherUiState
import com.scan.weather.weather.presentation.WeatherViewModel
import com.scan.weather.weather.view.adapter.CityDataRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class SearchCitiesFragment :
    BaseFragment<FragmentSearchCitiesBinding, WeatherUiState, WeatherViewModel>() {

    override fun onViewAttach() {
        super.onViewAttach()
        attachListener()

    }

    override fun getVM(): WeatherViewModel = getSharedViewModel()

    override fun getLayoutRes(): Int = R.layout.fragment_search_cities

    private fun attachListener() {
        binding.searchButton.setOnClickListener {
            if (viewModel.imageBitmap != null) viewModel.searchCities(binding.searchInput.text.toString())
            else Toast.makeText(
                requireContext(),
                R.string.please_take_photo_first,
                Toast.LENGTH_LONG
            ).show()
        }
        binding.takePhoto.setOnClickListener {
            checkPermissionAndOpenCamera()
        }
    }

    private fun checkPermissionAndOpenCamera() {
        context?.let {
            if (checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                requestPermission.launch(Manifest.permission.CAMERA)
            } else {
                takePhotoFromCamera()
            }
        }

    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            takePhotoFromCamera()
        }
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.extras
                val imageBitmap = data?.get("data") as Bitmap
                viewModel.imageBitmap = imageBitmap
                binding.previewImage.setImageBitmap(imageBitmap)
            }
        }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }


    override fun renderState(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.CitySearchResult -> {
                uiState.citiesUiModel.let {
                    binding.recyclerView.adapter =
                        CityDataRecyclerAdapter(uiState.citiesUiModel) { long, lat ->
                            viewModel.getCityWeatherData(long, lat)
                        }
                }
            }
            is WeatherUiState.ErrorLocalCities -> binding.noCities.visibility = View.VISIBLE
            is WeatherUiState.WeatherDetailRequested -> findNavController().navigate(R.id.action_searchCitiesFragment_to_weatherDetailsFragment)
            else -> Unit
        }
    }


}