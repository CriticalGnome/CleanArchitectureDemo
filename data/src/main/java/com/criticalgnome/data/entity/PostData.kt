package com.criticalgnome.data.entity

import com.squareup.moshi.Json

data class PostData(

    @Json(name = "userId")
    val userId: Int,

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "body")
    val body: String

)
