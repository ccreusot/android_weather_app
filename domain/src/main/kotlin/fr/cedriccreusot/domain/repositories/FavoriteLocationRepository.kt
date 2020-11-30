package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Favorite
import fr.cedriccreusot.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface FavoriteLocationRepository {
    suspend fun getFavorites(): Flow<Response<List<Favorite>>>
    suspend fun saveFavorite(city: City)
}