package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response

interface LocationRepository {
    fun getLocation(): Response<Location>
}