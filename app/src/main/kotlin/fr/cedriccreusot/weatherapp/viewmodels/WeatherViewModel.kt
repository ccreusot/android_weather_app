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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


sealed class Status {
    object LoadingStatus : Status()
    object RequestPermissionStatus : Status()
    data class WeathersStatus(val weathers: List<Weather>) : Status()
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
            fetchFavoriteLocationWeather()
                .combine(
                    fetchWeatherLocation()
                ) { favoriteWeathers, locationWeather ->
                    listOfNotNull(locationWeather) + favoriteWeathers
                }.collect {
                    weathersStatus.postValue(
                        Status.WeathersStatus(it)
                    )
                }
        }
    }

    private fun fetchWeatherLocation(): Flow<Weather?> {
        return locationRepository.getLocation()
            .distinctUntilChanged()
            .map { locationResponse ->
                if (locationResponse is Error) {
                    weathersStatus.postValue(Status.RequestPermissionStatus)
                    delay(500)
                    return@map null
                }

                when (val weatherResponse =
                    weatherRepository.getWeather((locationResponse as Success).data)) {
                    is Success -> weatherResponse.data
                    is Error -> null
                }
            }
    }

    private suspend fun fetchFavoriteLocationWeather(): Flow<List<Weather>> {
        return favoriteRepository
            .getFavorites()
            .distinctUntilChanged()
            .filter { it is Success }
            .mapNotNull { favoriteResponse ->
                val list = (favoriteResponse as Success).data.mapNotNull {
                    when (val weather = weatherRepository.getWeather(it.cityUri)) {
                        is Success -> weather.data
                        is Error -> null
                    }
                }
                list
            }
    }
}