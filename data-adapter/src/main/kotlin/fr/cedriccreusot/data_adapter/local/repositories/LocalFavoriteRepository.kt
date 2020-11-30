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
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.FavoriteLocationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class LocalFavoriteRepository(context: Context) : FavoriteLocationRepository {
    private val dataStore: DataStore<Preferences> = context.createDataStore(FAVORITES_PREFERENCES)

    override fun getFavorites(): Response<List<Favorite>> {
        val favoritesKey = preferencesKey<String>(FAVORITE_LIST)
        val favorites = runBlocking {
            dataStore.data.map { preferences ->
                val list = preferences[favoritesKey]?.split(';') ?: emptyList()
                list.map {
                    Favorite(it)
                }
            }.first()
        }
        return Success(favorites)
    }

    override fun saveFavorite(city: City) {
        val favoritesKey = preferencesKey<String>(FAVORITE_LIST)
        runBlocking {
            dataStore.edit { preferences ->
                val list = preferences[favoritesKey]?.split(';')?.toMutableList() ?: mutableListOf()
                city.uri?.let {
                    list.add(it)
                }
                preferences[favoritesKey] = list.joinToString(";")
            }
        }
    }
}

private const val FAVORITE_LIST = "favorite-list"
private const val FAVORITES_PREFERENCES = "favorites_preferences"