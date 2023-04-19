package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.Result

interface CommentRepository {
    suspend fun getComments(): Result<List<CommentModel>>
    suspend fun getComment(id: Int): Result<CommentModel>
}
