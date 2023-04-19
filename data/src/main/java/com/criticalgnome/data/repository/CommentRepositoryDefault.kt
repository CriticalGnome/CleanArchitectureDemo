package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val mapper: CommentMapper
) : CommentRepository {

    override suspend fun getComments(): Result<List<CommentModel>> {
        return ApiHandler.Builder<List<CommentData>, List<CommentModel>>()
            .setServiceExecutor { service.getComments() }
            .setMappingExecutor { data -> data.map { mapper.map(it) } }
            .handleApi()
    }

    override suspend fun getComment(id: Int): Result<CommentModel> {
        return ApiHandler.Builder<CommentData, CommentModel>()
            .setServiceExecutor { service.getComment(id) }
            .setMappingExecutor { mapper.map(it) }
            .handleApi()
    }
}
