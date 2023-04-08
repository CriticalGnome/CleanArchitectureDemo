package com.criticalgnome.data.db

import androidx.room.Dao
import androidx.room.Query
import com.criticalgnome.data.entity.PostRoom

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostRoom>
}
