package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.CommentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class CommentRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val mapper = mockk<CommentMapper>()
    private lateinit var sut: CommentRepository

    private val data = mockk<CommentData>()
    private val model = mockk<CommentModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        sut = CommentRepositoryDefault(service, mapper)
        every { mapper.map(data) } returns model
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(service, mapper)
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN comments fetched")
        fun getComments() {
            val response = mockk<Response<List<CommentData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(data)
            coEvery { service.getComments() } returns response

            runTest {
                val result = sut.getComments()
                if (result is Result.Success) assertEquals(listOf(model), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getComments()
                mapper.map(data)
            }
        }

        @Test
        @DisplayName("WHEN comment fetched")
        fun getComment() {
            val response = mockk<Response<CommentData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns data
            coEvery { service.getComment(id) } returns response

            runTest {
                val result = sut.getComment(id)
                if (result is Result.Success) assertEquals(model, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getComment(id)
                mapper.map(data)
            }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN comments fetched")
        fun getComments() {
            val response = mockk<Response<List<CommentData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getComments() } returns response

            runTest {
                val result = sut.getComments()
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                }
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComments() }
        }

        @Test
        @DisplayName("WHEN comment fetched")
        fun getComment() {
            val response = mockk<Response<CommentData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getComment(id) } returns response

            runTest {
                val result = sut.getComment(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                }
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComment(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN comments fetched")
        fun getComments() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message() } returns errorMessage
            coEvery { service.getComments() } throws throwable

            runTest {
                val result = sut.getComments()
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                }
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComments() }
        }

        @Test
        @DisplayName("WHEN comment fetched")
        fun getComment() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message() } returns errorMessage
            coEvery { service.getComment(id) } throws throwable

            runTest {
                val result = sut.getComment(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                }
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComment(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN comments fetched")
        fun getComments() {
            val throwable = mockk<Exception>()
            coEvery { service.getComments() } throws throwable

            runTest {
                val result = sut.getComments()
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComments() }
        }

        @Test
        @DisplayName("WHEN comment fetched")
        fun getComment() {
            val throwable = mockk<Exception>()
            coEvery { service.getComment(id) } throws throwable

            runTest {
                val result = sut.getComment(id)
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getComment(id) }
        }
    }
}
