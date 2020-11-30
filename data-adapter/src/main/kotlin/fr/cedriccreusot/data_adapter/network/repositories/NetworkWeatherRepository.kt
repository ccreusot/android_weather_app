package fr.cedriccreusot.data_adapter.network.repositories

import fr.cedriccreusot.data_adapter.network.WeatherService
import fr.cedriccreusot.domain.models.City
import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Weather
import fr.cedriccreusot.domain.repositories.WeatherRepository

class NetworkWeatherRepository(private val service: WeatherService) : WeatherRepository {
    override fun getWeather(city: String): Response<Weather> {
        runCatching {
            service.getCityWeather(city).execute().body()!!
        }.onSuccess {
            return Response.Success(
                Weather(
                    date = it.currentCondition?.date?.split('.')?.reversed()?.joinToString("-") ?: "",
                    currentTemperature = it.currentCondition?.tmp ?: 0,
                    temperatureMax = it.fcstDay0?.tmax ?: 0,
                    temperatureMin = it.fcstDay0?.tmin ?: 0,
                    city = City(
                        name = it.cityInfo?.name ?: "",
                        country =  it.cityInfo?.country,
                        zipCode = null,
                        uri = null,
                        countryCode = null
                    ),
                    condition = it.currentCondition?.condition ?: "",
                    iconCondition = it.currentCondition?.iconBig ?: ""
                )
            )
        }.onFailure {
            return Response.Error("Something went wrong")
        }
        return Response.Error("Something went wrong")
    }

    override fun getWeather(location: Location): Response<Weather> {
        runCatching {
            service.getLocalWeather(location.latitude, location.longitude).execute().body()!!
        }.onSuccess {
            return Response.Success(
                Weather(
                    date = it.currentCondition?.date?.split('.')?.reversed()?.joinToString("-") ?: "",
                    currentTemperature = it.currentCondition?.tmp ?: 0,
                    temperatureMax = it.fcstDay0?.tmax ?: 0,
                    temperatureMin = it.fcstDay0?.tmin ?: 0,
                    city = null,
                    condition = it.currentCondition?.condition ?: "",
                    iconCondition = it.currentCondition?.iconBig ?: ""
                )
            )
        }.onFailure {
            return Response.Error("Something went wrong")
        }
        return Response.Error("Something went wrong")
    }
}