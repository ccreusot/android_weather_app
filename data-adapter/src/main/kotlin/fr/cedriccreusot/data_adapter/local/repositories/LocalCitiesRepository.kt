package fr.cedriccreusot.data_adapter.local.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.CitiesRepository

class LocalCitiesRepository(private val networkRepository: CitiesRepository) : CitiesRepository {
    private lateinit var cities: List<City>

    override fun getCities(): Response<List<City>> {
        if (::cities.isInitialized) {
            return Success(cities)
        }

        return when(val response = networkRepository.getCities()) {
            is Success -> {
                cities = response.data
                response
            }
            is Error -> response
        }
    }
}