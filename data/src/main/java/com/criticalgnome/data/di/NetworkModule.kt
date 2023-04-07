package com.criticalgnome.data.di

import com.criticalgnome.data.BuildConfig
import com.criticalgnome.data.service.JsonPlaceholderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class JsonPlaceholder

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class Moshi

    @Provides
    @Singleton
    @Moshi
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    @JsonPlaceholder
    fun provideRetrofit(
        client: OkHttpClient,
        @Moshi factory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(JsonPlaceholderService.BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonPlaceholderService(
        @JsonPlaceholder retrofit: Retrofit
    ): JsonPlaceholderService {
        return retrofit.create(JsonPlaceholderService::class.java)
    }
}
