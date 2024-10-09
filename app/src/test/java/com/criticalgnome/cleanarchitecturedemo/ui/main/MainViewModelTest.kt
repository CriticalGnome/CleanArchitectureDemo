package com.criticalgnome.cleanarchitecturedemo.ui.main

import app.cash.turbine.turbineScope
import com.criticalgnome.cleanarchitecturedemo.TestDispatcherProvider
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.ErrorHandled
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.ExceptionHandled
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.PostsLoaded
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result.Error
import com.criticalgnome.domain.entity.Result.Exception
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.usecase.GetPostsUseCase
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val getPostsUseCase = mockk<GetPostsUseCase>()
    private val dispatcherProvider = TestDispatcherProvider()
    private val testScope = CoroutineScope(dispatcherProvider.default)
    private lateinit var sut: MainViewModel

    private val post = mockk<PostModel>()
    private val errorCode = 404
    private val errorMessage = "Page not found"
    private val throwable = mockk<Throwable>()

    @BeforeEach
    fun setUp() {
        sut = MainViewModel(
            getPostsUseCase,
            dispatcherProvider
        )
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(getPostsUseCase)
    }

    @Test
    @DisplayName("GIVEN working use case WHEN getPosts invoked THEN return posts in success state")
    fun getPostsSuccess() {
        // given
        coEvery { getPostsUseCase() } returns Success(listOf(post))

        runTest {
            turbineScope {
                // when
                sut.getPosts()
                val state = sut.viewState.testIn(testScope).awaitItem()
                // then
                assertTrue(state is PostsLoaded)
                assertEquals(listOf(post), (state as PostsLoaded).posts)
            }
        }
        coVerify { getPostsUseCase() }
    }

    @Test
    @DisplayName("GIVEN error use case WHEN getPosts invoked THEN return error state")
    fun getPostsError() {
        // given
        coEvery { getPostsUseCase() } returns Error(errorCode, errorMessage)

        runTest {
            turbineScope {
                // when
                sut.getPosts()
                val state = sut.viewState.testIn(testScope).awaitItem()
                // then
                assertTrue(state is ErrorHandled)
                val error = state as ErrorHandled
                assertEquals(errorCode, error.code)
                assertEquals(errorMessage, error.message)
            }
        }
        coVerify { getPostsUseCase() }
    }

    @Test
    @DisplayName("GIVEN use case with exception WHEN getPosts invoked THEN return exception state")
    fun getPostsException() {
        // given
        coEvery { getPostsUseCase() } returns Exception(throwable)

        runTest {
            turbineScope {
                // when
                sut.getPosts()
                val state = sut.viewState.testIn(testScope).awaitItem()
                // then
                assertTrue(state is ExceptionHandled)
                assertEquals(throwable, (state as ExceptionHandled).throwable)
            }
        }
        coVerify { getPostsUseCase() }
    }
}
