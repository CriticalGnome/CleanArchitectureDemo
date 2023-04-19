package com.criticalgnome.data.di

import com.criticalgnome.data.db.CommentDao
import com.criticalgnome.data.db.PostDao
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.repository.CommentRepositoryDefault
import com.criticalgnome.data.repository.PostRepositoryDefault
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.repository.CommentRepository
import com.criticalgnome.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(
        jsonPlaceholderService: JsonPlaceholderService,
        postDao: PostDao,
        commentDao: CommentDao,
        postMapper: PostMapper,
        commentMapper: CommentMapper
    ) : PostRepository {
        return PostRepositoryDefault(
            jsonPlaceholderService,
            postDao,
            commentDao,
            postMapper,
            commentMapper
        )
    }

    @Provides
    @Singleton
    fun provideCommentRepository(
        jsonPlaceholderService: JsonPlaceholderService,
        commentMapper: CommentMapper
    ) : CommentRepository {
        return CommentRepositoryDefault(
            jsonPlaceholderService,
            commentMapper
        )
    }
}
