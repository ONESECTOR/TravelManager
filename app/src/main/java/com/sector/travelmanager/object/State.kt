package com.sector.travelmanager.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class State(
    var name: String = "",
    val image: String = "",
    val terrain: String = ""
): Parcelable