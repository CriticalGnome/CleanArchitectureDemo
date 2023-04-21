package com.criticalgnome.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoData(

    @SerialName("userId")
    val userId: Int,

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("completed")
    val completed: Boolean

)
