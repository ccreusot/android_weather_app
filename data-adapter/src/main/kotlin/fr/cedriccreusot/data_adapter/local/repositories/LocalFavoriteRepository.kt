package fr.cedriccreusot.data_adapter.local.repositories

import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Favorite
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.FavoriteLocationRepository

class LocalFavoriteRepository(): FavoriteLocationRepository {

    override fun getFavorites(): Response<List<Favorite>> {
        return Success(emptyList())
    }

    override fun saveFavorite(city: City) {
    }
}