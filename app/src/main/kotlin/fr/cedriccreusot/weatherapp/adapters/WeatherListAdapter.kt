package fr.cedriccreusot.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.domain.models.Favorite
import fr.cedriccreusot.weatherapp.databinding.ItemWeatherBinding

class WeatherListAdapter : ListAdapter<Favorite, FavoriteViewHolder>(object :
    DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        // TODO("Not yet implemented")
    }
}

class FavoriteViewHolder(binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
}