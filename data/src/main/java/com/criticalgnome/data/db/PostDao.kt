package com.criticalgnome.data.db

import androidx.room.*
import com.criticalgnome.data.entity.PostRoom

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostRoom>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPost(id: Int): PostRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: PostRoom)

    @Update
    suspend fun updatePosts(vararg posts: PostRoom)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Delete
    fun delete(post: PostRoom)
}
