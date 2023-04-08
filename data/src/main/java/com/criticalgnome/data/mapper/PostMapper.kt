package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PostData
import com.criticalgnome.data.entity.PostRoom
import com.criticalgnome.domain.entity.PostModel

interface PostMapper {
    fun map(postData: PostData): PostModel
    fun map(postModel: PostModel): PostData
    fun dbMap(postData: PostData): PostRoom
    fun dbMap(postRoom: PostRoom): PostModel
}
