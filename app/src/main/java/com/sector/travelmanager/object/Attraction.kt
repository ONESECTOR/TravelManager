package com.sector.travelmanager.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attraction(
    val name: String = "",
    val image: String = "",
    val city: String = "",
    val basicInformation: String = "",
    val history: String = "",
    val location: String = ""
): Parcelable