package fr.cedriccreusot.data_adapter.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.cedriccreusot.data_adapter.models.City
import fr.cedriccreusot.data_adapter.models.CityJsonAdapter
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface WeatherService {
    @GET("list-cities") fun getCities(): Call<Map<String, City>>

    companion object {
        fun create(): WeatherService {
            val moshi = Moshi.Builder().build().let {
                val type = Types.newParameterizedType(Map::class.java, String::class.java, City::class.java)
                val tmp = it.newBuilder().add(City::class.java, CityJsonAdapter(it))
                    .build()
                val adapter = tmp.adapter<Map<String, City>>(type)
                tmp.newBuilder().add(type, adapter).build()
            }

            return Retrofit.Builder()
                .baseUrl("https://prevision-meteo.ch/services/json/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(WeatherService::class.java)
        }
    }
}