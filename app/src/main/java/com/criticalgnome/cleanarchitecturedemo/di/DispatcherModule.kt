package com.criticalgnome.cleanarchitecturedemo.di

import com.criticalgnome.cleanarchitecturedemo.dispatcher.DispatcherProvider
import com.criticalgnome.cleanarchitecturedemo.dispatcher.DispatcherProviderDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderDefault()
    }
}
