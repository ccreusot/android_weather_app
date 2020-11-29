package fr.cedriccreusot.data_adapter.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "name") val name: String,
    @Json(name = "npa") val zipCode: String?,
    @Json(name = "region") val region: String?,
    @Json(name = "country") val countryCode: String,
    @Json(name = "url") val uriPath: String
)