package fr.cedriccreusot.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import fr.cedriccreusot.domain.models.Weather
import fr.cedriccreusot.weatherapp.R
import fr.cedriccreusot.weatherapp.databinding.ItemWeatherBinding

class WeatherListAdapter : ListAdapter<Weather, WeatherViewHolder>(object :
    DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
    private val resources get() = itemView.resources

    fun bind(weather: Weather) {
        with(binding) {
            currentTemperature.text = resources.getString(R.string.temperature, weather.currentTemperature)
            location.text = resources.getString(R.string.weather_around_me)
            weather.city?.let {
                location.text = resources.getString(R.string.weather_location, weather.city?.name, weather.city?.country)
            }
            temperatureMax.text = resources.getString(R.string.temperature_max, weather.temperatureMax)
            temperatureMin.text = resources.getString(R.string.temperature_min, weather.temperatureMin)
            iconCondition.load(weather.iconCondition)
        }
    }
}