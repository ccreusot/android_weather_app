package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.Option
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Weather
import fr.cedriccreusot.domain.repositories.WeatherRepository

class FetchWeatherUseCase(private val repository: WeatherRepository) {
    operator fun invoke(option: Option) : Response<Weather> {
        return when (option) {
            is Option.FromLocation -> repository.getWeather(option.location)
            is Option.FromCity -> repository.getWeather(option.city)
        }
    }
}