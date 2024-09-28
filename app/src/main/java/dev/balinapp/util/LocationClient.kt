package dev.balinapp.util

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocation(interval: Long): Flow<Location>
}