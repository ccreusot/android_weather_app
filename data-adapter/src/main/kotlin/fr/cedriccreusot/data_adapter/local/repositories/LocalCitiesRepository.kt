package fr.cedriccreusot.data_adapter.local.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.cedriccreusot.data_adapter.models.City
import fr.cedriccreusot.data_adapter.models.CityJsonAdapter
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.repositories.CitiesRepository
import kotlinx.coroutines.flow.*

private typealias DomainCity = fr.cedriccreusot.domain.models.City

class LocalCitiesRepository(
    context: Context,
    private val networkRepository: CitiesRepository
) : CitiesRepository {

    private val citiesCacheKey = preferencesKey<String>(CITIES_CACHE_KEY)
    private val dataStore: DataStore<Preferences> = context.createDataStore(CITIES_CACHE)
    private val moshi = Moshi.Builder().build()
    private val cityJsonAdapter =
        moshi.newBuilder().add(City::class.java, CityJsonAdapter(moshi)).build()
            .adapter<List<City>>(
                Types.newParameterizedType(
                    List::class.java, City::class.java
                )
            )

    override fun getCities(): Flow<Response<List<DomainCity>>> {
        return flow {
            val list = loadFromCache().firstOrNull()
            if (!list.isNullOrEmpty()) {
                emit(Response.Success(list))
                return@flow
            }

            networkRepository.getCities()
                .onEach {
                    if (it is Response.Success) {
                        saveInCache(it.data)
                    }
                }
                .collect { networkResponse ->
                    emit(networkResponse)
                }
        }
    }

    private suspend fun saveInCache(cities: List<DomainCity>) {
        dataStore.edit { cache ->
            cache[citiesCacheKey] =
                cityJsonAdapter.toJson(cities.map {
                    it.toNetworkCity()
                })
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private fun loadFromCache(): Flow<List<DomainCity>> {
        return dataStore.data.map { cache ->
            cache[citiesCacheKey]?.let {
                cityJsonAdapter.fromJson(it)
            } ?: emptyList()
        }.map { cities ->
            cities.map {
                it.toDomain()
            }
        }
    }

    private fun City.toDomain() = DomainCity(
        name = name,
        zipCode = zipCode,
        countryCode = countryCode,
        uri = uriPath,
        country = null,
    )

    private fun DomainCity.toNetworkCity() = City(
        name = name,
        zipCode = zipCode,
        region = null,
        countryCode = countryCode!!,
        uriPath = uri!!
    )
}

private const val CITIES_CACHE = "cities_cache"
private const val CITIES_CACHE_KEY = "cities"
