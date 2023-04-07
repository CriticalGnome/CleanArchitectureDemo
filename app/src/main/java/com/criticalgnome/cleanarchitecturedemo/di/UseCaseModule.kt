package com.criticalgnome.cleanarchitecturedemo.di

import com.criticalgnome.domain.repository.PostRepository
import com.criticalgnome.domain.usecase.GetPostsUseCase
import com.criticalgnome.domain.usecase.GetPostsUseCaseDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPostsUseCase(postRepository: PostRepository): GetPostsUseCase {
        return GetPostsUseCaseDefault(postRepository)
    }
}
