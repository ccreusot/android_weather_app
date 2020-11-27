package fr.cedriccreusot.domain.repositories

import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Weather

interface WeatherRepository {
    fun getWeather(city: String): Response<Weather>
    fun getWeather(location: Location): Response<Weather>
}