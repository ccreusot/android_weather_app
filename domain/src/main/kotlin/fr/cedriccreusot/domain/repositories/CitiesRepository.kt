package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities() : Flow<Response<List<City>>>
}