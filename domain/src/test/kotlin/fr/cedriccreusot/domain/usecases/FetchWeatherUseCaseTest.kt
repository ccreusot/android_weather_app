package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.*
import fr.cedriccreusot.domain.repositories.WeatherRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class FetchWeatherUseCaseTest {

    @Test
    fun `we want to retrieve the weather giving the location from the repository but the repository doesn't know the location`() {
        val repository = mockk<WeatherRepository>()
        val useCase = FetchWeatherUseCase(repository)
        val expected = Response.Error<Weather>("Unknown location")

        every { repository.getWeather(any<Location>()) }.returns(expected)

        val result = useCase(Option.FromLocation(Location(-48.8566, -2.3522)))
        result `should be equal to` expected
        verify { repository.getWeather(any<Location>()) }
    }

    @Test
    fun `we want to retrieve the weather giving the location from the repository`() {
        val repository = mockk<WeatherRepository>()
        val useCase = FetchWeatherUseCase(repository)
        val expected = Response.Success(
            Weather(
                date = "2020-11-27",
                currentTemperature = 5,
                temperatureMax = 10,
                temperatureMin = 0,
                city = City(
                    name = "Paris",
                    zipCode = "75008",
                    country = "France",
                    countryCode = null,
                    uri = ""
                ),
                condition = "Sunny",
                iconCondition = "sunny_icon"
            )
        )

        every { repository.getWeather(any<Location>()) }.returns(expected)

        val result = useCase(Option.FromLocation(Location(48.8566, 2.3522)))
        result `should be equal to` expected
        verify { repository.getWeather(any<Location>()) }
    }

    @Test
    fun `wwe want to retrieve the weather giving the city from the repository but the repository doesn't know the location`() {
        val repository = mockk<WeatherRepository>()
        val useCase = FetchWeatherUseCase(repository)
        val expected = Response.Error<Weather>("Unknown location")

        every { repository.getWeather(any<String>()) }.returns(expected)

        val result = useCase(Option.FromCity(""))
        result `should be equal to` expected
        verify { repository.getWeather(any<String>()) }
    }

    @Test
    fun `we want to retrieve the weather giving the city from the repository`() {
        val repository = mockk<WeatherRepository>()
        val useCase = FetchWeatherUseCase(repository)
        val expected = Response.Success(
            Weather(
                date = "2020-11-27",
                currentTemperature = 5,
                temperatureMax = 10,
                temperatureMin = 0,
                city = City(
                    name = "Paris",
                    zipCode = "75008",
                    country = "France",
                    countryCode = null,
                    uri = ""
                ),
                condition = "Sunny",
                iconCondition = "sunny_icon"
            )
        )

        every { repository.getWeather(any<String>()) }.returns(expected)

        val result = useCase(Option.FromCity("paris"))
        result `should be equal to` expected
        verify { repository.getWeather(any<String>()) }
    }
}