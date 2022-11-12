package com.scan.weather.weather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.scan.weather.databinding.CityListItemBinding
import com.scan.weather.weather.presentation.WeatherUiState

class CityWeatherRecyclerAdapter(
    private val citiesList: List<WeatherUiState.CityWeatherCardUiModel>,
    val onCityClicked: (cityName: String) -> Unit
) : RecyclerView.Adapter<CityWeatherRecyclerAdapter.CityWeatherViewHolder>() {

    class CityWeatherViewHolder(val binding: CityListItemBinding) : ViewHolder(binding.root) {
        fun bind(cityWeatherCardUiModel: WeatherUiState.CityWeatherCardUiModel) {
            binding.city = cityWeatherCardUiModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        return CityWeatherViewHolder(
            CityListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onCityClicked(citiesList[position].cityName) }
        holder.bind(citiesList[position])
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }
}