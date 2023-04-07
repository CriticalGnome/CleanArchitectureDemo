package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result

interface PostRepository {
    suspend fun getPosts(): Result<List<PostModel>>
    suspend fun getPost(id: Int): Result<PostModel>
    suspend fun getPostComments(id: Int): Result<List<CommentModel>>
    suspend fun postPosts(posts: List<PostModel>): Result<List<PostModel>>
    suspend fun putPost(id: Int, post: PostModel): Result<PostModel>
    suspend fun deletePost(id: Int): Result<Unit>
}
