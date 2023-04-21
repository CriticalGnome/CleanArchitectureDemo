package com.criticalgnome.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("address")
    val address: AddressData,

    @SerialName("phone")
    val phone: String,

    @SerialName("website")
    val website: String,

    @SerialName("company")
    val company: CompanyData

)
