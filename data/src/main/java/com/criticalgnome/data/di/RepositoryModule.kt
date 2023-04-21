package com.criticalgnome.data.di

import com.criticalgnome.data.db.CommentDao
import com.criticalgnome.data.db.PostDao
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.mapper.TodoMapper
import com.criticalgnome.data.mapper.UserMapper
import com.criticalgnome.data.repository.CommentRepositoryDefault
import com.criticalgnome.data.repository.PostRepositoryDefault
import com.criticalgnome.data.repository.TodoRepositoryDefault
import com.criticalgnome.data.repository.UserRepositoryDefault
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.repository.CommentRepository
import com.criticalgnome.domain.repository.PostRepository
import com.criticalgnome.domain.repository.TodoRepository
import com.criticalgnome.domain.repository.UserRepository
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

    @Provides
    @Singleton
    fun provideUserRepository(
        jsonPlaceholderService: JsonPlaceholderService,
        userMapper: UserMapper
    ) : UserRepository {
        return UserRepositoryDefault(
            jsonPlaceholderService,
            userMapper
        )
    }

    @Provides
    @Singleton
    fun provideTodoRepository(
        jsonPlaceholderService: JsonPlaceholderService,
        todoMapper: TodoMapper
    ) : TodoRepository {
        return TodoRepositoryDefault(
            jsonPlaceholderService,
            todoMapper
        )
    }
}
