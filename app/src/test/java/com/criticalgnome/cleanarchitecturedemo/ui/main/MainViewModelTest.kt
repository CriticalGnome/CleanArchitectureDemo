package com.criticalgnome.cleanarchitecturedemo.ui.main

import app.cash.turbine.test
import com.criticalgnome.cleanarchitecturedemo.TestDispatcherProvider
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.PostsLoaded
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result.Success
import com.criticalgnome.domain.usecase.GetPostsUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val getPostsUseCase = mockk<GetPostsUseCase>()
    private lateinit var sut: MainViewModel

    @BeforeEach
    fun setUp() {
        sut = MainViewModel(
            getPostsUseCase,
            TestDispatcherProvider()
        )
    }

    @Test
    fun getPosts() {
        val post = mockk<PostModel>()
        coEvery { getPostsUseCase() } returns flowOf(Success(listOf(post)))

        runTest {
            sut.getPosts()
            sut.viewState.test {
                val state = awaitItem()
                if (state is PostsLoaded) assertEquals(listOf(post), state.posts)
                else throw TestNotWorkingProperlyException()
            }
        }
    }
}
