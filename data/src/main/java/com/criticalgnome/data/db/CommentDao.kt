package com.criticalgnome.data.db

import androidx.room.Dao
import androidx.room.Query
import com.criticalgnome.data.entity.CommentRoom

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getComments(): List<CommentRoom>
}
