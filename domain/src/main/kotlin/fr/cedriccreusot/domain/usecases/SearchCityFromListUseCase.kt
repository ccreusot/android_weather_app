package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.City

class SearchCityFromListUseCase {
    operator fun invoke(city: String, cities: List<City>): List<City> {
        if (city.isEmpty()) {
            return cities
        }

        return cities.filter {
            it.name.contains(city, true)
        }
    }
}