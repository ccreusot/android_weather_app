package fr.cedriccreusot.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.weatherapp.R
import fr.cedriccreusot.weatherapp.databinding.ItemCityBinding

typealias OnSelectItem = (item: City) -> Unit

class CitiesAdapter(private val onSelectItem: OnSelectItem? = null) :
    ListAdapter<City, CityViewHolder>(object :
        DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onSelectItem
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CityViewHolder(
    private val binding: ItemCityBinding,
    private val onSelectItem: OnSelectItem?
) : RecyclerView.ViewHolder(binding.root) {
    private val resources get() = itemView.resources

    fun bind(city: City) {
        with(binding) {
            root.setOnClickListener {
                onSelectItem?.invoke(city)
            }
            cityName.text = city.name
            countryFlag.text = when (city.countryCode) {
                "BEL" -> resources.getString(R.string.belgium_flag)
                "FRA" -> resources.getString(R.string.france_flag)
                "CHE" -> resources.getString(R.string.swiss_flag)
                else -> ""
            }
        }
    }
}