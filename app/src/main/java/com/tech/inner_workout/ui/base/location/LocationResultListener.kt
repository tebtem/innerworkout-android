package com.tech.inner_workout.ui.base.location
import android.location.Location

interface LocationResultListener {
    fun getLocation(location: Location)
}