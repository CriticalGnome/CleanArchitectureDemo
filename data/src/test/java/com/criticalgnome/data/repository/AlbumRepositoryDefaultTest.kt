package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.AlbumData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.AlbumMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.AlbumModel
import com.criticalgnome.domain.entity.Result.Error
import com.criticalgnome.domain.entity.Result.Exception
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.repository.AlbumRepository
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

internal class AlbumRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val mapper = mockk<AlbumMapper>()
    private lateinit var sut: AlbumRepository

    private val data = mockk<AlbumData>()
    private val model = mockk<AlbumModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        every { mapper.map(data) } returns model
        sut = AlbumRepositoryDefault(service, mapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(service, mapper)
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN albums fetched")
        fun getAlbums() {
            val response = mockk<Response<List<AlbumData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(data)
            coEvery { service.getAlbums() } returns response

            runTest {
                val result = sut.getAlbums()
                if (result is Success) assertEquals(listOf(model), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getAlbums()
                mapper.map(data)
            }
        }

        @Test
        @DisplayName("WHEN album fetched")
        fun getAlbum() {
            val response = mockk<Response<AlbumData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns data
            coEvery { service.getAlbum(id) } returns response

            runTest {
                val result = sut.getAlbum(id)
                if (result is Success) assertEquals(model, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getAlbum(id)
                mapper.map(data)
            }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN albums fetched")
        fun getAlbums() {
            val response = mockk<Response<List<AlbumData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getAlbums() } returns response

            runTest {
                val result = sut.getAlbums()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbums() }
        }

        @Test
        @DisplayName("WHEN album fetched")
        fun getAlbum() {
            val response = mockk<Response<AlbumData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getAlbum(id) } returns response

            runTest {
                val result = sut.getAlbum(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbum(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN albums fetched")
        fun getAlbums() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getAlbums() } throws throwable

            runTest {
                val result = sut.getAlbums()
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbums() }
        }

        @Test
        @DisplayName("WHEN album fetched")
        fun getAlbum() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getAlbum(id) } throws throwable

            runTest {
                val result = sut.getAlbum(id)
                if (result is Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbum(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN albums fetched")
        fun getAlbums() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getAlbums() } throws throwable

            runTest {
                val result = sut.getAlbums()
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbums() }
        }

        @Test
        @DisplayName("WHEN album fetched")
        fun getAlbum() {
            val throwable = mockk<kotlin.Exception>()
            coEvery { service.getAlbum(id) } throws throwable

            runTest {
                val result = sut.getAlbum(id)
                if (result is Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getAlbum(id) }
        }
    }
}
