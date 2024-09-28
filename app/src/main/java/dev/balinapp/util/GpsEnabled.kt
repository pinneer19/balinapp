package dev.balinapp.util

import android.content.Context
import android.location.LocationManager
import androidx.fragment.app.Fragment

fun Fragment.gpsEnabled(): Boolean {
    val locationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}