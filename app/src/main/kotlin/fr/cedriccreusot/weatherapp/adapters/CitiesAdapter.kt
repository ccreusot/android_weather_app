package fr.cedriccreusot.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.weatherapp.databinding.ItemCityBinding

typealias OnSelectItem = (item: City) -> Unit

class CitiesAdapter(onSelectItem: OnSelectItem) : ListAdapter<City, CityViewHolder>(object :
    DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: City) {
        with(binding) {
            cityName.text = city.name
            countryFlag.text = when (city.countryCode) {
                "BEL" -> "\uD83C\uDDE7\uD83C\uDDEA"
                "FRA" -> "\uD83C\uDDEB\uD83C\uDDF7"
                "CHE" -> "\uD83C\uDDE8\uD83C\uDDED"
                else -> ""
            }
        }
    }
}