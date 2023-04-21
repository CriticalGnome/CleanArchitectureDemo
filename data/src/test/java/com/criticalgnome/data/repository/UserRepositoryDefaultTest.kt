package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.UserData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.UserMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.Result.*
import com.criticalgnome.domain.entity.UserModel
import com.criticalgnome.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class UserRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val mapper = mockk<UserMapper>()
    private lateinit var sut: UserRepository

    private val data = mockk<UserData>()
    private val model = mockk<UserModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        every { mapper.map(data) } returns model
        sut = UserRepositoryDefault(service, mapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(service, mapper)
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN users fetched")
        fun getUsers() {
            val response = mockk<Response<List<UserData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(data)
            coEvery { service.getUsers() } returns response

            runTest {
                val result = sut.getUsers()
                if (result is Success) assertEquals(listOf(model), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getUsers()
                mapper.map(data)
            }
        }

        @Test
        @DisplayName("WHEN user fetched")
        fun getUser() {
            val response = mockk<Response<UserData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns data
            coEvery { service.getUser(id) } returns response

            runTest {
                val result = sut.getUser(id)
                if (result is Success) assertEquals(model, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getUser(id)
                mapper.map(data)
            }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN users fetched")
        fun getUsers() {
            val response = mockk<Response<List<UserData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getUsers() } returns response

            runTest {
                val result = sut.getUsers()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder { service.getUsers() }
        }

        @Test
        @DisplayName("WHEN user fetched")
        fun getUser() {
            val response = mockk<Response<UserData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getUser(id) } returns response

            runTest {
                val result = sut.getUser(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getUser(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN users fetched")
        fun getUsers() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getUsers() } throws throwable

            runTest {
                val result = sut.getUsers()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder { service.getUsers() }
        }

        @Test
        @DisplayName("WHEN user fetched")
        fun getUser() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getUser(id) } throws throwable

            runTest {
                val result = sut.getUser(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getUser(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN users fetched")
        fun getUsers() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getUsers() } throws throwable

            runTest {
                val result = sut.getUsers()
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder { service.getUsers() }
        }

        @Test
        @DisplayName("WHEN user fetched")
        fun getUser() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getUser(id) } throws throwable

            runTest {
                val result = sut.getUser(id)
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getUser(id) }
        }
    }
}
