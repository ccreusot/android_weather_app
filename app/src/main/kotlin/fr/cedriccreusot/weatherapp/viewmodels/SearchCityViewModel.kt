package fr.cedriccreusot.weatherapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.CitiesRepository
import fr.cedriccreusot.domain.usecases.SearchCityFromListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchCityViewModel @ViewModelInject constructor(
    private val searchUseCase: SearchCityFromListUseCase,
    private val cityRepository: CitiesRepository
) : ViewModel() {

    private val cities: MutableLiveData<List<City>> = MutableLiveData(emptyList())

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery.debounce(200)
                .distinctUntilChanged()
                .mapLatest {
                    val response = cityRepository.getCities()
                    if (response is Success) {
                        searchUseCase(it, response.data)
                    } else {
                        emptyList()
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    cities.value = it
                }
        }
    }

    fun cities(): LiveData<List<City>> = cities

    fun search(cityName: String) {
        searchQuery.value = cityName
    }
}