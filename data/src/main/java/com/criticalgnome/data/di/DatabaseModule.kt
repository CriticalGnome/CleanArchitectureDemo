package com.criticalgnome.data.di

import android.content.Context
import androidx.room.Room
import com.criticalgnome.data.db.CommentDao
import com.criticalgnome.data.db.JsonPlaceholderDB
import com.criticalgnome.data.db.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJsonPlaceholderDB(
        @ApplicationContext context: Context
    ) : JsonPlaceholderDB {
        return Room.databaseBuilder(
            context,
            JsonPlaceholderDB::class.java,
            "json_placeholder_db"
        ).addMigrations(
            JsonPlaceholderDB.MIGRATION_1_2
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(db: JsonPlaceholderDB) : PostDao {
        return db.getPostDao()
    }

    @Provides
    @Singleton
    fun provideCommentDao(db: JsonPlaceholderDB) : CommentDao {
        return db.getCommentDao()
    }
}
