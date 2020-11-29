package fr.cedriccreusot.domain.usecases

import fr.cedriccreusot.domain.models.City
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class SearchCityFromListUseCaseTest {

    @Test
    fun `we search for a city that doesn't exist, we should retrieve an empty list`() {
        val useCase = SearchCityFromListUseCase()
        val cities = listOf<City>()
        val expected = emptyList<City>()

        val result = useCase("Unknown", cities)

        result `should be equal to` expected
    }

    @Test
    fun `Search city is empty we should get all the cities`() {
        val useCase = SearchCityFromListUseCase()
        val cities = listOf(
            City(name = "Paris", null, null, null, "paris"),
            City(name = "Bordeaux", null, null, null, "bordeaux"),
            City(name = "Montpellier", null, null, null, "montpellier"),
            City(name = "Rennes", null, null, null, "rennes"),
            City(name = "Metz", null, null, null, "Metz"),
        )

        val expected = cities

        val result = useCase("", cities)

        result `should be equal to` expected
    }

    @Test
    fun `We're searching few letter, we should retrieve a list corresponding to those first letter`() {
        val useCase = SearchCityFromListUseCase()
        val cities = listOf(
            City(name = "Paris", null, null, null, "paris"),
            City(name = "Bordeaux", null, null, null, "bordeaux"),
            City(name = "Montpellier", null, null, null, "montpellier"),
            City(name = "Rennes", null, null, null, "rennes"),
            City(name = "Metz", null, null, null, "Metz"),
        )

        val expected =
            listOf(
                City(name = "Montpellier", null, null, null, "montpellier"),
                City(name = "Metz", null, null, null, "Metz"),
            )

        val result = useCase("M", cities)

        result `should be equal to` expected
    }
}