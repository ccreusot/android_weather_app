package fr.cedriccreusot.weatherapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.models.Weather
import fr.cedriccreusot.domain.repositories.FavoriteLocationRepository
import fr.cedriccreusot.domain.repositories.LocationRepository
import fr.cedriccreusot.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


sealed class Status {
    object LoadingStatus: Status()
    data class WeathersStatus(val weathers: List<Weather>): Status()
}

class WeatherViewModel @ViewModelInject constructor(
    private val favoriteRepository: FavoriteLocationRepository,
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val weathersStatus: MutableLiveData<Status> by lazy {
        fetchWeathers()
        MutableLiveData(Status.LoadingStatus)
    }

    fun weathers(): LiveData<Status> = weathersStatus

    private fun fetchWeathers() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResponse = when (val response = locationRepository.getLocation()) {
                is Success -> weatherRepository.getWeather(response.data)
                is Error -> Error(response.message)
            }
            when (weatherResponse) {
                is Success -> {
                    weathersStatus.postValue(
                        Status.WeathersStatus(listOf(weatherResponse.data))
                    )
                }
                is Error -> Unit
            }
        }
    }
}