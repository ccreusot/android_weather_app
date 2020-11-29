package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Favorite
import fr.cedriccreusot.domain.models.Response

interface FavoriteLocationRepository {
    fun getFavorites(): Response<List<Favorite>>
    fun saveFavorite(city: City)
}