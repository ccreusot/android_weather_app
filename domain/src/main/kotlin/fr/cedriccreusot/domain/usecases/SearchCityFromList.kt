package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Success

class SearchCityFromList {
    operator fun invoke(city: String, cities: List<City>): Response<List<City>> {
        if (city.isEmpty()) {
            return Success(cities)
        }

        return cities.filter {
            city in it.name
        }.takeIf {
            it.isNotEmpty()
        }?.let {
            Success(it)
        } ?: Error("Not found")
    }
}