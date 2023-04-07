package com.criticalgnome.data.di

import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.CommentMapperDefault
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.mapper.PostMapperDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun providePostMapper(): PostMapper {
        return PostMapperDefault()
    }

    @Provides
    @Singleton
    fun provideCommentMapper(): CommentMapper {
        return CommentMapperDefault()
    }
}
