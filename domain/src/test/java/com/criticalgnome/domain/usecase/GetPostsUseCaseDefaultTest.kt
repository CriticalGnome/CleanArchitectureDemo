package com.criticalgnome.domain.usecase

import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result.Error
import com.criticalgnome.domain.entity.Result.Exception
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class GetPostsUseCaseDefaultTest {

    private val postRepository = mockk<PostRepository>()
    private lateinit var sut: GetPostsUseCase

    private val post = mockk<PostModel>()
    private val errorCode = 404
    private val errorMessage = "Page not found"
    private val throwable = mockk<Throwable>()

    @BeforeEach
    fun setUp() {
        sut = GetPostsUseCaseDefault(postRepository)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(postRepository)
    }

    @Test
    @DisplayName("GIVEN working repository WHEN use case invoked THEN return success")
    fun workingRepository() {
        // given
        coEvery { postRepository.getPosts() } returns Success(listOf(post))
        runTest {
            // when
            val result = sut()
            // then
            assertTrue(result is Success)
            assertEquals(listOf(post), (result as Success).data)
        }
        coVerify { postRepository.getPosts() }
    }

    @Test
    @DisplayName("GIVEN error repository WHEN use case invoked THEN return error")
    fun errorRepository() {
        // given
        coEvery { postRepository.getPosts() } returns Error(errorCode, errorMessage)
        runTest {
            // when
            val result = sut()
            // then
            assertTrue(result is Error)
            val error = result as Error
            assertEquals(errorCode, error.code)
            assertEquals(errorMessage, error.message)
        }
        coVerify { postRepository.getPosts() }
    }

    @Test
    @DisplayName("GIVEN repository with exception WHEN use case invoked THEN return exception")
    fun exceptionRepository() {
        // given
        coEvery { postRepository.getPosts() } returns Exception(throwable)
        runTest {
            // when
            val result = sut()
            // then
            assertTrue(result is Exception)
            assertEquals(throwable, (result as Exception).throwable)
        }
        coVerify { postRepository.getPosts() }
    }
}
