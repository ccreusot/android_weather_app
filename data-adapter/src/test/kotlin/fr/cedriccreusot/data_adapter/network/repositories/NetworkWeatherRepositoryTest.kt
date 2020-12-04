package fr.cedriccreusot.data_adapter.network.repositories

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.cedriccreusot.data_adapter.JsonFileUtils
import fr.cedriccreusot.data_adapter.WeatherServiceMocks
import fr.cedriccreusot.data_adapter.models.*
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import org.amshove.kluent.`should be equal to`
import org.junit.Test

typealias DomainWeather = fr.cedriccreusot.domain.models.Weather

class NetworkWeatherRepositoryTest {

    @Test
    fun `We get the weather for the city it should return an error when an error is returned from the webservice`() {
        val service = WeatherServiceMocks.createServiceThatFail()

        val repository = NetworkWeatherRepository(service)

        val result = repository.getWeather("city")

        result `should be equal to` Response.Error("Something went wrong")
    }

    @Test
    fun `We get the weather for the location it should return an error when an error is returned from the webservice`() {
        val service = WeatherServiceMocks.createServiceThatFail()

        val repository = NetworkWeatherRepository(service)

        val result = repository.getWeather(Location(0.2222, 2.3333))

        result `should be equal to` Response.Error("Something went wrong")
    }

    @Test
    fun `We get the weather for the location we should return a Weather object`() {
        val locationFile = JsonFileUtils.readJsonFile("weather-location.json")
        val type = Types.newParameterizedType(Map::class.java, String::class.java, HourlyData::class.java)
        val moshi = Moshi.Builder().build().let {
            val tmp = it.newBuilder()
                .add(Weather::class.java, WeatherJsonAdapter(it))
                .add(CityInfo::class.java, CityInfoJsonAdapter(it))
                .add(ForecastInfo::class.java, ForecastInfoJsonAdapter(it))
                .add(CurrentCondition::class.java, CurrentConditionJsonAdapter(it))
                .add(ForecastDay::class.java, ForecastDayJsonAdapter(it))
                .add(HourlyData::class.java, HourlyDataJsonAdapter(it))
                .build()
            val adapter = tmp.adapter<Map<String, HourlyData>>(type)
            tmp.newBuilder().add(type, adapter).build()
        }
        val adapter = moshi.adapter(Weather::class.java)
        val service = WeatherServiceMocks.createServiceThatSucceed(weatherLocation = adapter.fromJson(locationFile))

        val repository = NetworkWeatherRepository(service)

        val result = repository.getWeather(Location(0.2222, 2.33333))

        result `should be equal to` Response.Success(
            DomainWeather(
                date = "2020-11-29",
                currentTemperature = 0,
                temperatureMin = -3,
                temperatureMax = 7,
                city = null,
                condition = "Nuit claire",
                iconCondition = "https://prevision-meteo.ch/style/images/icon/nuit-claire-big.png"
            )
        )
    }

    @Test
    fun `We get the weather for the city we should return a Weather object`() {
        val cityFile = JsonFileUtils.readJsonFile("weather-city.json")
        val type = Types.newParameterizedType(Map::class.java, String::class.java, HourlyData::class.java)
        val moshi = Moshi.Builder().build().let {
            val tmp = it.newBuilder()
                .add(Weather::class.java, WeatherJsonAdapter(it))
                .add(CityInfo::class.java, CityInfoJsonAdapter(it))
                .add(ForecastInfo::class.java, ForecastInfoJsonAdapter(it))
                .add(CurrentCondition::class.java, CurrentConditionJsonAdapter(it))
                .add(ForecastDay::class.java, ForecastDayJsonAdapter(it))
                .add(HourlyData::class.java, HourlyDataJsonAdapter(it))
                .build()
            val adapter = tmp.adapter<Map<String, HourlyData>>(type)
            tmp.newBuilder().add(type, adapter).build()
        }
        val adapter = moshi.adapter(Weather::class.java)
        val service = WeatherServiceMocks.createServiceThatSucceed(weatherCity = adapter.fromJson(cityFile))

        val repository = NetworkWeatherRepository(service)

        val result = repository.getWeather("paris")

        result `should be equal to` Response.Success(
            DomainWeather(
                date = "2020-11-29",
                currentTemperature = 6,
                temperatureMin = 3,
                temperatureMax = 8,
                city = City(
                    name = "Paris",
                    country = "France",
                    countryCode = null,
                    uri = null,
                    zipCode = null
                ),
                condition = "Nuit claire",
                iconCondition = "https://prevision-meteo.ch/style/images/icon/nuit-claire-big.png"
            )
        )
    }
}