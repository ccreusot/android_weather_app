package fr.cedriccreusot.domain.models

data class City(
    val name: String,
    val zipCode: String?,
    val country: String?,
    val countryCode: String?,
    val uri: String?
)
