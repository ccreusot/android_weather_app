package fr.cedriccreusot.data_adapter.network.repositories

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.cedriccreusot.data_adapter.JsonFileUtils
import fr.cedriccreusot.data_adapter.WeatherServiceMocks
import fr.cedriccreusot.data_adapter.models.City
import fr.cedriccreusot.data_adapter.models.CityJsonAdapter
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Success
import org.amshove.kluent.`should be equal to`
import org.junit.Test

typealias DomainCity = fr.cedriccreusot.domain.models.City


class NetworkCitiesRepositoryTest {

    @Test
    fun `Our repository should return an error when an error is returned from the webservice`() {
        val service = WeatherServiceMocks.createServiceThatFail()

        val repository = NetworkCitiesRepository(service)

        val result = repository.getCities()

        result `should be equal to` Error("Something went wrong")
    }

    @Test
    fun `Our repository should return a list of domain city from the webservice response`() {
        val json = JsonFileUtils.readJsonFile("list-cities.json")
        val type = Types.newParameterizedType(Map::class.java, String::class.java, City::class.java)
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Map<String, City>> = moshi.newBuilder().add(City::class.java, CityJsonAdapter(moshi)).build().adapter(type)
        val service = WeatherServiceMocks.createServiceThatSucceed(adapter.fromJson(json))

        val repository = NetworkCitiesRepository(service)

        val result = repository.getCities()

        result `should be equal to` Success(listOf(
            DomainCity(
                name = "Aaigem",
                zipCode = "9420",
                country = null,
                countryCode = "BEL",
                uri = "aaigem"
            ),
            DomainCity(
                name = "Aalbeke",
                zipCode = "8511",
                country = null,
                countryCode = "BEL",
                uri = "aalbeke"
            ),
            DomainCity(
                name = "Aalst",
                zipCode = "9300",
                country = null,
                countryCode = "BEL",
                uri = "aalst"
            )
        ))
    }
}