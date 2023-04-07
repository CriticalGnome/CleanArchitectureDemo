package com.criticalgnome.data.repository

import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.data.service.handleApi
import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val postMapper: PostMapper,
    private val commentMapper: CommentMapper
) : PostRepository {

    override suspend fun getPosts(): Result<List<PostModel>> {
        return handleApi(
            execute = { service.getPosts() },
            mapping = { data -> data.map { postMapper.map(it) } }
        )
    }

    override suspend fun getPost(id: Int): Result<PostModel> {
        return handleApi(
            execute = { service.getPost(id) },
            mapping = { data -> postMapper.map(data) }
        )
    }

    override suspend fun getPostComments(id: Int): Result<List<CommentModel>> {
        return handleApi(
            execute = { service.getPostComments(id) },
            mapping = { data -> data.map { commentMapper.map(it) } }
        )
    }

    override suspend fun postPosts(posts: List<PostModel>): Result<List<PostModel>> {
        return handleApi(
            execute = { service.postPosts(posts.map { postMapper.map(it) }) },
            mapping = { data -> data.map { postMapper.map(it) } }
        )
    }

    override suspend fun putPost(id: Int, post: PostModel): Result<PostModel> {
        return handleApi(
            execute = { service.putPost(id, postMapper.map(post)) },
            mapping = { data -> postMapper.map(data) }
        )
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return handleApi(
            execute = { service.deletePost(id) },
            mapping = { /* do nothing */ }
        )
    }
}
