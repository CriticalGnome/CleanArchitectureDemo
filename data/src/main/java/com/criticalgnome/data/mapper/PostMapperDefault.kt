package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PostData
import com.criticalgnome.domain.entity.PostModel

class PostMapperDefault : PostMapper {

    override fun map(postData: PostData): PostModel {
        return PostModel(
            userId = postData.userId,
            id = postData.id,
            title = postData.title,
            body = postData.body
        )
    }

    override fun map(postModel: PostModel): PostData {
        return PostData(
            userId = postModel.userId,
            id = postModel.id,
            title = postModel.title,
            body = postModel.body
        )
    }
}
