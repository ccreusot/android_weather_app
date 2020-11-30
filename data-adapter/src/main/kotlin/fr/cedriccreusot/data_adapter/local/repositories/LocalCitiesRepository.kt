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
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.CitiesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private typealias DomainCity = fr.cedriccreusot.domain.models.City

class LocalCitiesRepository(
    context: Context,
    private val networkRepository: CitiesRepository
) : CitiesRepository {

    private val dataStore: DataStore<Preferences> = context.createDataStore(CITIES_CACHE)
    private val moshi = Moshi.Builder().build()
    private val cityJsonAdapter =
        moshi.newBuilder().add(City::class.java, CityJsonAdapter(moshi)).build()
            .adapter<List<City>>(
                Types.newParameterizedType(
                    List::class.java, City::class.java
                )
            )

    override fun getCities(): Response<List<DomainCity>> {
        val citiesCacheKey = preferencesKey<String>(CITIES_CACHE_KEY)
        val cities = runBlocking {
            dataStore.data.map { cache ->
                cache[citiesCacheKey]?.let {
                    cityJsonAdapter.fromJson(it)
                } ?: emptyList()
            }.first()
        }

        if (cities.isNotEmpty()) {
            return Success(cities.map {
                DomainCity(
                    name = it.name,
                    zipCode = it.zipCode,
                    countryCode = it.countryCode,
                    uri = it.uriPath,
                    country = null,
                )
            })
        }

        val networkResponse = networkRepository.getCities()
        if (networkResponse is Success) {
            runBlocking {
                dataStore.edit { cache ->
                    cache[citiesCacheKey] = cityJsonAdapter.toJson(networkResponse.data.map {
                        City(
                            name = it.name,
                            zipCode = it.zipCode,
                            region = null,
                            countryCode = it.countryCode!!,
                            uriPath =  it.uri!!
                        )
                    })
                }
            }
        }
        return networkResponse
    }
}

private const val CITIES_CACHE = "cities_cache"
private const val CITIES_CACHE_KEY = "cities"
