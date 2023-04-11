package com.criticalgnome.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.criticalgnome.data.entity.CommentRoom
import com.criticalgnome.data.entity.PostRoom

@Database(
    entities = [
        PostRoom::class,
        CommentRoom::class
    ],
    version = 2
)
abstract class JsonPlaceholderDB : RoomDatabase() {
    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) =
                database.execSQL("ALTER TABLE comments RENAME COLUMN postId TO post_id")
        }
    }
}
