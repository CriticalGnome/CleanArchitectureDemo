package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.PhotoData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.PhotoMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.PhotoModel
import com.criticalgnome.domain.entity.Result.Error
import com.criticalgnome.domain.entity.Result.Exception
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.repository.PhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

internal class PhotoRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val mapper = mockk<PhotoMapper>()
    private lateinit var sut: PhotoRepository

    private val data = mockk<PhotoData>()
    private val model = mockk<PhotoModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        every { mapper.map(data) } returns model
        sut = PhotoRepositoryDefault(service, mapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(service, mapper)
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN photos fetched")
        fun getPhotos() {
            val response = mockk<Response<List<PhotoData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(data)
            coEvery { service.getPhotos() } returns response

            runTest {
                val result = sut.getPhotos()
                if (result is Success) assertEquals(listOf(model), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getPhotos()
                mapper.map(data)
            }
        }

        @Test
        @DisplayName("WHEN photo fetched")
        fun getPhoto() {
            val response = mockk<Response<PhotoData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns data
            coEvery { service.getPhoto(id) } returns response

            runTest {
                val result = sut.getPhoto(id)
                if (result is Success) assertEquals(model, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getPhoto(id)
                mapper.map(data)
            }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN photos fetched")
        fun getPhotos() {
            val response = mockk<Response<List<PhotoData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getPhotos() } returns response

            runTest {
                val result = sut.getPhotos()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhotos() }
        }

        @Test
        @DisplayName("WHEN photo fetched")
        fun getPhoto() {
            val response = mockk<Response<PhotoData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getPhoto(id) } returns response

            runTest {
                val result = sut.getPhoto(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhoto(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN photos fetched")
        fun getPhotos() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getPhotos() } throws throwable

            runTest {
                val result = sut.getPhotos()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhotos() }
        }

        @Test
        @DisplayName("WHEN photo fetched")
        fun getPhoto() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getPhoto(id) } throws throwable

            runTest {
                val result = sut.getPhoto(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhoto(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN photos fetched")
        fun getPhotos() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getPhotos() } throws throwable

            runTest {
                val result = sut.getPhotos()
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhotos() }
        }

        @Test
        @DisplayName("WHEN photo fetched")
        fun getPhoto() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getPhoto(id) } throws throwable

            runTest {
                val result = sut.getPhoto(id)
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPhoto(id) }
        }
    }
}
