package fr.cedriccreusot.weatherapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.CitiesRepository
import fr.cedriccreusot.domain.usecases.SearchCityFromListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
class SearchCityViewModel @ViewModelInject constructor(
    private val searchUseCase: SearchCityFromListUseCase,
    private val cityRepository: CitiesRepository
) : ViewModel() {
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    val cities: LiveData<List<City>> = searchQuery.debounce(200)
        .distinctUntilChanged()
        .map {
            val response = cityRepository.getCities()
            if (response is Success) {
                searchUseCase(it, response.data)
            } else {
                emptyList()
            }
        }
        .flowOn(Dispatchers.IO)
        .asLiveData()

    fun search(cityName: String) {
        searchQuery.value = cityName
    }
}