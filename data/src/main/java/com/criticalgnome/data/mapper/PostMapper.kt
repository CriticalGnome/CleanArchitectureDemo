package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PostData
import com.criticalgnome.domain.entity.PostModel

interface PostMapper {
    fun map(postData: PostData): PostModel
    fun map(postModel: PostModel): PostData
}
