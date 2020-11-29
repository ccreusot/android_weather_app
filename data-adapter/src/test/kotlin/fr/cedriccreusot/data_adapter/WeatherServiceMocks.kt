package fr.cedriccreusot.data_adapter

import fr.cedriccreusot.data_adapter.models.City
import fr.cedriccreusot.data_adapter.models.Weather
import fr.cedriccreusot.data_adapter.network.WeatherService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import java.io.IOException

object WeatherServiceMocks {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://prevision-meteo.ch/services/json/")
        .build()
    private val behaviorDelegate: BehaviorDelegate<WeatherService> =
        MockRetrofit.Builder(retrofit).build().create(WeatherService::class.java)

    fun createServiceThatFail(): WeatherService =
        object : WeatherService {
            override fun getCities(): Call<Map<String, City>> {
                return behaviorDelegate.returning(Calls.failure<IOException>(IOException()))
                    .getCities()
            }

            override fun getLocalWeather(latitude: Double, longitude: Double): Call<Weather> {
                return behaviorDelegate.returning(Calls.failure<IOException>(IOException()))
                    .getLocalWeather(latitude, longitude)
            }

            override fun getCityWeather(cityUri: String): Call<Weather> {
                return behaviorDelegate.returning(Calls.failure<IOException>(IOException()))
                    .getCityWeather(cityUri)
            }
        }

    fun createServiceThatSucceed(
        cities: Map<String, City>? = null,
        weatherCity: Weather? = null,
        weatherLocation: Weather? = null
    ): WeatherService =
        object : WeatherService {
            override fun getCities(): Call<Map<String, City>> {
                return behaviorDelegate.returningResponse(cities!!).getCities()
            }

            override fun getLocalWeather(latitude: Double, longitude: Double): Call<Weather> {
                return behaviorDelegate.returningResponse(weatherLocation).getLocalWeather(latitude, longitude)
            }

            override fun getCityWeather(cityUri: String): Call<Weather> {
                return behaviorDelegate.returningResponse(weatherCity).getCityWeather(cityUri)
            }
        }
}