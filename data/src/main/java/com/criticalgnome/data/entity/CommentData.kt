package com.criticalgnome.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentData(

    @SerialName("postId")
    val postId: Int,

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("body")
    val body: String

)
