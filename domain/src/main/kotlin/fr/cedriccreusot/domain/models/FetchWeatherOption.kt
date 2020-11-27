package fr.cedriccreusot.domain.models

sealed class Option
data class FromLocation(val location: Location): Option()
data class FromCity(val city: String): Option()
