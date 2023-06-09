package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.entity.CommentRoom
import com.criticalgnome.domain.entity.CommentModel

interface CommentMapper {
    fun map(commentData: CommentData): CommentModel
    fun map(commentModel: CommentModel): CommentData
    fun dbMap(commentData: CommentData): CommentRoom
    fun dbMap(commentRoom: CommentRoom): CommentModel
}
