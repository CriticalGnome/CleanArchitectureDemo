package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.TodoData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.TodoMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.Result.Error
import com.criticalgnome.domain.entity.Result.Exception
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.entity.TodoModel
import com.criticalgnome.domain.repository.TodoRepository
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

internal class TodoRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val mapper = mockk<TodoMapper>()
    private lateinit var sut: TodoRepository

    private val data = mockk<TodoData>()
    private val model = mockk<TodoModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        every { mapper.map(data) } returns model
        sut = TodoRepositoryDefault(service, mapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(service, mapper)
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN todos fetched")
        fun getTodos() {
            val response = mockk<Response<List<TodoData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(data)
            coEvery { service.getTodos() } returns response

            runTest {
                val result = sut.getTodos()
                if (result is Success) assertEquals(listOf(model), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getTodos()
                mapper.map(data)
            }
        }

        @Test
        @DisplayName("WHEN todo fetched")
        fun getTodo() {
            val response = mockk<Response<TodoData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns data
            coEvery { service.getTodo(id) } returns response

            runTest {
                val result = sut.getTodo(id)
                if (result is Success) assertEquals(model, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getTodo(id)
                mapper.map(data)
            }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN todos fetched")
        fun getTodos() {
            val response = mockk<Response<List<TodoData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getTodos() } returns response

            runTest {
                val result = sut.getTodos()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodos() }
        }

        @Test
        @DisplayName("WHEN todo fetched")
        fun getTodo() {
            val response = mockk<Response<TodoData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getTodo(id) } returns response

            runTest {
                val result = sut.getTodo(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodo(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN todos fetched")
        fun getTodos() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getTodos() } throws throwable

            runTest {
                val result = sut.getTodos()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodos() }
        }

        @Test
        @DisplayName("WHEN todo fetched")
        fun getTodo() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getTodo(id) } throws throwable

            runTest {
                val result = sut.getTodo(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodo(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN todos fetched")
        fun getTodos() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getTodos() } throws throwable

            runTest {
                val result = sut.getTodos()
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodos() }
        }

        @Test
        @DisplayName("WHEN todo fetched")
        fun getTodo() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getTodo(id) } throws throwable

            runTest {
                val result = sut.getTodo(id)
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getTodo(id) }
        }
    }
}
