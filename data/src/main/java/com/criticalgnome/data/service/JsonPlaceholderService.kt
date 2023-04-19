package com.criticalgnome.data.service

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.entity.PostData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JsonPlaceholderService {

    // region posts

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostData>>

    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<PostData>

    @GET("/post/{id}/comments")
    suspend fun getPostComments(@Path("id") id: Int): Response<List<CommentData>>

    @POST("/posts")
    suspend fun postPosts(@Body posts: List<PostData>): Response<List<PostData>>

    @PUT("/posts/{id}")
    suspend fun putPost(@Path("id") id: Int, @Body post: PostData): Response<PostData>

    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>

    // endregion

    // region comments

    @GET("/comments")
    suspend fun getComments(): Response<List<CommentData>>

    @GET("/comments/{id}")
    suspend fun getComment(@Path("id") id: Int): Response<CommentData>

    // endregion

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}
