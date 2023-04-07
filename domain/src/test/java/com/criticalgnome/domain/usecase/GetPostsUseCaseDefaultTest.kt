package com.criticalgnome.domain.usecase

import app.cash.turbine.testIn
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetPostsUseCaseDefaultTest {

    private val postRepository = mockk<PostRepository>()
    private lateinit var sut: GetPostsUseCase

    private val testScope = CoroutineScope(UnconfinedTestDispatcher())

    @BeforeEach
    fun setUp() {
        sut = GetPostsUseCaseDefault(postRepository)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(postRepository)
    }

    @Test
    fun invoke() {
        val result = mockk<Result<List<PostModel>>>()
        coEvery { postRepository.getPosts() } returns result

        runTest { assertEquals(result, sut().testIn(testScope).awaitItem()) }

        coVerify { postRepository.getPosts() }
    }
}
