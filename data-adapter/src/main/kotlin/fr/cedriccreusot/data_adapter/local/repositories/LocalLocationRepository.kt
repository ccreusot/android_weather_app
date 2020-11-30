package fr.cedriccreusot.data_adapter.local.repositories

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import androidx.core.content.ContextCompat
import fr.cedriccreusot.domain.models.Location
import fr.cedriccreusot.domain.models.Response
import fr.cedriccreusot.domain.repositories.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalLocationRepository(private val context: Context) : LocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLocation(): Flow<Response<Location>> {
        return flow {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            while (!checkPermissionGranted()) {
                emit(Response.Error("Permission not granted"))
                delay(1000)
            }

            val provider = locationManager.getBestProvider(
                Criteria().apply {
                    accuracy = Criteria.ACCURACY_FINE
                    powerRequirement = Criteria.NO_REQUIREMENT
                },
                true
            )
            val location =
                locationManager.getLastKnownLocation(provider ?: LocationManager.GPS_PROVIDER)
                    ?.let {
                        Location(it.latitude, it.longitude)
                    }
            emit(if (location != null) Response.Success(location) else Response.Error("Location not found"))
        }
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