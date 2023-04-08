package com.criticalgnome.data.repository

import com.criticalgnome.data.db.CommentDao
import com.criticalgnome.data.db.PostDao
import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.entity.PostData
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val postMapper: PostMapper,
    private val commentMapper: CommentMapper
) : PostRepository {

    override suspend fun getPosts(): Result<List<PostModel>> {
        return ApiHandler.Builder<List<PostData>, List<PostModel>>()
            .setServiceExecutor { service.getPosts() }
            .setMappingExecutor { data -> data.map { postMapper.map(it) } }
            .handleApi()
    }

    override suspend fun getPost(id: Int): Result<PostModel> {
        return ApiHandler.Builder<PostData, PostModel>()
            .setServiceExecutor { service.getPost(id) }
            .setMappingExecutor { data -> postMapper.map(data) }
            .handleApi()
    }

    override suspend fun getPostComments(id: Int): Result<List<CommentModel>> {
        return ApiHandler.Builder<List<CommentData>, List<CommentModel>>()
            .setServiceExecutor { service.getPostComments(id) }
            .setMappingExecutor { data -> data.map { commentMapper.map(it) } }
            .handleApi()
    }

    override suspend fun postPosts(posts: List<PostModel>): Result<List<PostModel>> {
        return ApiHandler.Builder<List<PostData>, List<PostModel>>()
            .setServiceExecutor { service.postPosts(posts.map { postMapper.map(it) }) }
            .setMappingExecutor { data -> data.map { postMapper.map(it) } }
            .handleApi()
    }

    override suspend fun putPost(id: Int, post: PostModel): Result<PostModel> {
        return ApiHandler.Builder<PostData, PostModel>()
            .setServiceExecutor { service.putPost(id, postMapper.map(post)) }
            .setMappingExecutor { data -> postMapper.map(data) }
            .handleApi()
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return ApiHandler.Builder<Unit, Unit>()
            .setServiceExecutor { service.deletePost(id) }
            .setMappingExecutor { /* do nothing */ }
            .handleApi()
    }
}
