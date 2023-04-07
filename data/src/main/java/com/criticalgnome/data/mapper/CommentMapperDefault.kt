package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.domain.entity.CommentModel

class CommentMapperDefault : CommentMapper {

    override fun map(commentData: CommentData): CommentModel {
        return CommentModel(
            postId = commentData.postId,
            id = commentData.postId,
            name = commentData.name,
            email = commentData.email,
            body = commentData.body
        )
    }

    override fun map(commentModel: CommentModel): CommentData {
        return CommentData(
            postId = commentModel.postId,
            id = commentModel.postId,
            name = commentModel.name,
            email = commentModel.email,
            body = commentModel.body
        )
    }
}
