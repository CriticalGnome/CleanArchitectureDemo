package com.criticalgnome.data.db

import androidx.room.*
import com.criticalgnome.data.entity.CommentRoom

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getComments(): List<CommentRoom>

    @Query("SELECT * FROM comments WHERE id = :id")
    suspend fun getComment(id: Int): CommentRoom

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    suspend fun getCommentsByPostId(postId: Int): List<CommentRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(vararg comments: CommentRoom)

    @Update
    suspend fun updateComments(vararg comments: CommentRoom)

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Delete
    fun delete(comment: CommentRoom)
}
