package fr.cedriccreusot.domain.models

data class Weather(
    val date: String,
    val currentTemperature: Int,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val city: City?,
    val condition: String,
    val iconCondition: String
)