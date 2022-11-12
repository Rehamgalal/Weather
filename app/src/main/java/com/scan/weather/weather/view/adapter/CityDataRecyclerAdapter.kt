package com.scan.weather.weather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scan.weather.databinding.CitySearchListItemBinding
import com.scan.weather.weather.presentation.WeatherUiState

class CityDataRecyclerAdapter(
    private val citiesList: List<WeatherUiState.CityUiModel>,
    val onCityClicked: (long: Double, lat: Double) -> Unit
) : RecyclerView.Adapter<CityDataRecyclerAdapter.CityDataViewHolder>() {
    class CityDataViewHolder(val binding: CitySearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cityUiModel: WeatherUiState.CityUiModel) {
            binding.city = cityUiModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityDataViewHolder {
        return CityDataViewHolder(
            CitySearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: CityDataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onCityClicked(
                citiesList[position].long,
                citiesList[position].lat
            )
        }
        holder.bind(citiesList[position])
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

}