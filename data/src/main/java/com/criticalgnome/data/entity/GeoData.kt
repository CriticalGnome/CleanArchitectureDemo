package com.criticalgnome.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoData(

    @SerialName("lat")
    val lat: String,

    @SerialName("lng")
    val lng: String

)
