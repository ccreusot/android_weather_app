package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocation(): Flow<Response<Location>>
}