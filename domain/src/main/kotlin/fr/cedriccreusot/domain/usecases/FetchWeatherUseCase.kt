package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.*
import fr.cedriccreusot.domain.repositories.WeatherRepository

class FetchWeatherUseCase(private val repository: WeatherRepository) {
    operator fun invoke(option: Option) : Response<Weather> {
        return when (option) {
            is FromLocation -> repository.getWeather(option.location)
            is FromCity -> repository.getWeather(option.city)
        }
    }
}