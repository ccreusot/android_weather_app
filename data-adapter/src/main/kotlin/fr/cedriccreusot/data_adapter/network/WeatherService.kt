package fr.cedriccreusot.data_adapter.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.cedriccreusot.data_adapter.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.ParameterizedType

interface WeatherService {
    @GET("list-cities")
    fun getCities(): Call<Map<String, City>>

    @GET("lat={latitude}lng={longitude}")
    fun getLocalWeather(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Call<Weather>

    @GET("{cityUri}")
    fun getCityWeather(@Path("cityUri") cityUri: String): Call<Weather>

    companion object {

        private val citiesType: ParameterizedType = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            City::class.java
        )

        private val hourlyDataType: ParameterizedType = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            HourlyData::class.java
        )

        private fun buildJsonParser() : Moshi {
            return Moshi.Builder().build().let {
                val tmp = it.newBuilder()
                    .add(City::class.java, CityJsonAdapter(it))
                    .add(Weather::class.java, WeatherJsonAdapter(it))
                    .add(CityInfo::class.java, CityInfoJsonAdapter(it))
                    .add(ForecastInfo::class.java, ForecastInfoJsonAdapter(it))
                    .add(CurrentCondition::class.java, CurrentConditionJsonAdapter(it))
                    .add(ForecastDay::class.java, ForecastDayJsonAdapter(it))
                    .add(HourlyData::class.java, HourlyDataJsonAdapter(it))
                    .build()
                val mapCityAdapter = tmp.adapter<Map<String, City>>(citiesType)
                val mapHourlyDataAdapter = tmp.adapter<Map<String, HourlyData>>(hourlyDataType)
                tmp.newBuilder()
                    .add(citiesType, mapCityAdapter)
                    .add(hourlyDataType, mapHourlyDataAdapter)
                    .build()
            }
        }

        fun create(): WeatherService {
            return Retrofit.Builder()
                .baseUrl("https://prevision-meteo.ch/services/json/")
                .addConverterFactory(MoshiConverterFactory.create(buildJsonParser()))
                .build()
                .create(WeatherService::class.java)
        }
    }
}