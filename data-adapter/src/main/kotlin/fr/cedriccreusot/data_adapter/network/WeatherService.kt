package fr.cedriccreusot.data_adapter.network

import fr.cedriccreusot.data_adapter.models.City
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface WeatherService {
    @GET("list-cities") fun getCities(): Call<Map<String, City>>

    companion object {
        fun create(): WeatherService = Retrofit.Builder()
            .baseUrl("https://prevision-meteo.ch/services/json/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}