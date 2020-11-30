package fr.cedriccreusot.data_adapter.local.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Favorite
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.repositories.FavoriteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalFavoriteRepository(context: Context) : FavoriteLocationRepository {
    private val dataStore: DataStore<Preferences> = context.createDataStore(FAVORITES_PREFERENCES)
    private val favoritesKey = preferencesKey<String>(FAVORITE_LIST)

    override suspend fun getFavorites(): Flow<Response<List<Favorite>>> {
        return dataStore.data.map { preferences ->
            val list = preferences[favoritesKey]?.split(';') ?: emptyList()
            Response.Success(list.map {
                Favorite(it)
            })
        }
    }

    override suspend fun saveFavorite(city: City) {
        dataStore.edit { preferences ->
            val list = preferences[favoritesKey]?.split(';')?.toMutableList() ?: mutableListOf()
            city.uri?.let {
                list.add(it)
            }
            preferences[favoritesKey] = list.joinToString(";")
        }
    }
}

private const val FAVORITE_LIST = "favorite-list"
private const val FAVORITES_PREFERENCES = "favorites_preferences"