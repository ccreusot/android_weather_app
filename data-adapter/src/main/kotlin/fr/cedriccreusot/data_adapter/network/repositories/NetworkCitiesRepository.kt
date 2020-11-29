package fr.cedriccreusot.data_adapter.network.repositories

import fr.cedriccreusot.data_adapter.network.WeatherService
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.CitiesRepository

class NetworkCitiesRepository(private val service: WeatherService): CitiesRepository {
    override fun getCities(): Response<List<City>> {
        runCatching {
            service.getCities().execute().body()!!
        }.onSuccess { cities ->
            return Success(cities.entries.map { entry ->
                City(
                    name = entry.value.name,
                    zipCode = entry.value.zipCode,
                    countryCode = entry.value.countryCode,
                    uri = entry.value.uriPath,
                    country = null,
                )
            })
        }.onFailure {
            return Error("Something went wrong")
        }
        return Error("Something went wrong")
    }
}