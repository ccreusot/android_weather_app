package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Response

interface CitiesRepository {
    fun getCities() : Response<List<City>>
}