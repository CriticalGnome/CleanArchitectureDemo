package com.criticalgnome.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyData(

    @SerialName("name")
    val name: String,

    @SerialName("catchPhrase")
    val catchPhrase: String,

    @SerialName("bs")
    val bs: String

)
