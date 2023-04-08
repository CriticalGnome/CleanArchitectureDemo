package com.criticalgnome.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.criticalgnome.data.entity.CommentRoom
import com.criticalgnome.data.entity.PostRoom

@Database(
    entities = [
        PostRoom::class,
        CommentRoom::class
    ],
    version = 1
)
abstract class JsonPlaceholderDB : RoomDatabase() {
    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao
}
