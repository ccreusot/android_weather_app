package fr.cedriccreusot.data_adapter.network.repositories

import fr.cedriccreusot.data_adapter.network.WeatherService
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.repositories.CitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkCitiesRepository(private val service: WeatherService): CitiesRepository {
    override fun getCities(): Flow<Response<List<City>>> {
        return flow {
            runCatching {
                service.getCities().execute().body()!!
            }.onSuccess { cities ->
                emit(Response.Success(cities.entries.map { entry ->
                    City(
                        name = entry.value.name,
                        zipCode = entry.value.zipCode,
                        countryCode = entry.value.countryCode,
                        uri = entry.value.uriPath,
                        country = null,
                    )
                }))
            }.onFailure {
                emit(Response.Error<List<City>>("Something went wrong"))
            }
        }
    }
}