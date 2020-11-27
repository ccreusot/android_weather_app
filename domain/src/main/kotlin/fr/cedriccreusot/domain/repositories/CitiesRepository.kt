package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.City

interface CitiesRepository {
    fun getCities() : List<City>
}