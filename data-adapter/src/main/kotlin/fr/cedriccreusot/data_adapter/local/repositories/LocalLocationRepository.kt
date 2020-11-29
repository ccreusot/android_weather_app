package fr.cedriccreusot.data_adapter.local.repositories

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import androidx.core.content.ContextCompat
import fr.cedriccreusot.domain.models.Error
import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.models.Success
import fr.cedriccreusot.domain.repositories.LocationRepository

class LocalLocationRepository(private val context: Context) : LocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLocation(): Response<Location> {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!checkPermissionGranted()) {
            return Error("Permission not granted")
        }

        val provider = locationManager.getBestProvider(
            Criteria().apply {
                accuracy = Criteria.ACCURACY_FINE
                powerRequirement = Criteria.NO_REQUIREMENT
            },
            true
        )
        val location =
            locationManager.getLastKnownLocation(provider ?: LocationManager.GPS_PROVIDER)?.let {
                Location(it.latitude, it.longitude)
            }

        return if (location != null) Success(location) else Error("Location not found")
    }

    private fun checkPermissionGranted(): Boolean =
        (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)

}