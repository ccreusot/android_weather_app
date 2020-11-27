package fr.cedriccreusot.domain.models

data class Weather(
    val date: String,
    val currentTemperature: Int,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val windSpeed: Int,
    val windGust: Int,
    val windDirection: String,
    val pressure: Double,
    val humidity: Int,
    val city: City,
    val condition: String,
    val iconCondition: String
)