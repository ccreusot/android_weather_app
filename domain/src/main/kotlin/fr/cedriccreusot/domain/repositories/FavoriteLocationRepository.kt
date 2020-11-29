package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.Favorite
import javax.xml.ws.Response

interface FavoriteLocationRepository {
    fun getFavorites(): Response<List<Favorite>>
}