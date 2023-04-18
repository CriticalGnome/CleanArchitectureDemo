package com.criticalgnome.data.di

import com.criticalgnome.data.BuildConfig
import com.criticalgnome.data.service.JsonPlaceholderService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class KotlinSerialization

    @Provides
    @Singleton
    @Moshi
    fun provideMoshiConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    @KotlinSerialization
    fun provideKotlinSerializationConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
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
        @KotlinSerialization factory: Converter.Factory
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
