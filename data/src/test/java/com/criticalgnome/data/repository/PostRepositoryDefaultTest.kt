package com.criticalgnome.data.repository

import com.criticalgnome.data.db.CommentDao
import com.criticalgnome.data.db.PostDao
import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.data.entity.PostData
import com.criticalgnome.data.exception.TestNotWorkingProperlyException
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.CommentModel
import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class PostRepositoryDefaultTest {

    private val service = mockk<JsonPlaceholderService>()
    private val postDao = mockk<PostDao>()
    private val commentDao = mockk<CommentDao>()
    private val postMapper = mockk<PostMapper>()
    private val commentMapper = mockk<CommentMapper>()
    private lateinit var sut: PostRepository

    private val postData = mockk<PostData>()
    private val postModel = mockk<PostModel>()
    private val commentData = mockk<CommentData>()
    private val commentModel = mockk<CommentModel>()
    private val id = 42
    private val errorCode = 404
    private val errorMessage = "Page not found"

    @BeforeEach
    fun setUp() {
        sut = PostRepositoryDefault(
            service,
            postDao,
            commentDao,
            postMapper,
            commentMapper
        )
        every { postMapper.map(postData) } returns postModel
        every { postMapper.map(postModel) } returns postData
        every { commentMapper.map(commentData) } returns commentModel
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(
            service,
            postMapper,
            commentMapper
        )
    }

    @Nested
    @DisplayName("GIVEN working repository THEN returns Success")
    inner class WorkingRepository {

        @Test
        @DisplayName("WHEN posts fetched")
        fun getPosts() {
            val response = mockk<Response<List<PostData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(postData)
            coEvery { service.getPosts() } returns response

            runTest {
                val result = sut.getPosts()
                if (result is Result.Success) assertEquals(listOf(postModel), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getPosts()
                postMapper.map(postData)
            }
        }

        @Test
        @DisplayName("WHEN post fetched")
        fun getPost() {
            val response = mockk<Response<PostData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns postData
            coEvery { service.getPost(id) } returns response

            runTest {
                val result = sut.getPost(id)
                if (result is Result.Success) assertEquals(postModel, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getPost(id)
                postMapper.map(postData)
            }
        }

        @Test
        @DisplayName("WHEN post comments fetched")
        fun getPostComments() {
            val response = mockk<Response<List<CommentData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(commentData)
            coEvery { service.getPostComments(id) } returns response

            runTest {
                val result = sut.getPostComments(id)
                if (result is Result.Success) assertEquals(listOf(commentModel), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                service.getPostComments(id)
                commentMapper.map(commentData)
            }
        }

        @Test
        @DisplayName("WHEN posts added")
        fun postPosts() {
            val response = mockk<Response<List<PostData>>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns listOf(postData)
            coEvery { service.postPosts(listOf(postData)) } returns response

            runTest {
                val result = sut.postPosts(listOf(postModel))
                if (result is Result.Success) assertEquals(listOf(postModel), result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.postPosts(listOf(postData))
                postMapper.map(postData)
            }
        }

        @Test
        @DisplayName("WHEN posts updated")
        fun putPost() {
            val response = mockk<Response<PostData>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns postData
            coEvery { service.putPost(id, postData) } returns response

            runTest {
                val result = sut.putPost(id, postModel)
                if (result is Result.Success) assertEquals(postModel, result.data)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.putPost(id, postData)
                postMapper.map(postData)
            }
        }

        @Suppress("USELESS_IS_CHECK")
        @Test
        @DisplayName("WHEN posts deleted")
        fun deletePost() {
            val response = mockk<Response<Unit>>()
            every { response.isSuccessful } returns true
            every { response.body() } returns Unit
            coEvery { service.deletePost(id) } returns response

            runTest {
                val result = sut.deletePost(id)
                if (result is Result.Success) assertTrue(result.data is Unit)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.deletePost(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN error response THEN returns Error")
    inner class ErrorResponse {

        @Test
        @DisplayName("WHEN posts fetched")
        fun getPosts() {
            val response = mockk<Response<List<PostData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getPosts() } returns response

            runTest {
                val result = sut.getPosts()
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPosts() }
        }

        @Test
        @DisplayName("WHEN post fetched")
        fun getPost() {
            val response = mockk<Response<PostData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getPost(id) } returns response

            runTest {
                val result = sut.getPost(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPost(id) }
        }

        @Test
        @DisplayName("WHEN post comments fetched")
        fun getPostComments() {
            val response = mockk<Response<List<CommentData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.getPostComments(id) } returns response

            runTest {
                val result = sut.getPostComments(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPostComments(id) }
        }

        @Test
        @DisplayName("WHEN posts added")
        fun postPosts() {
            val response = mockk<Response<List<PostData>>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.postPosts(listOf(postData)) } returns response

            runTest {
                val result = sut.postPosts(listOf(postModel))
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.postPosts(listOf(postData))
            }
        }

        @Test
        @DisplayName("WHEN posts updated")
        fun putPost() {
            val response = mockk<Response<PostData>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.putPost(id, postData) } returns response

            runTest {
                val result = sut.putPost(id, postModel)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.putPost(id, postData)
            }
        }

        @Test
        @DisplayName("WHEN posts deleted")
        fun deletePost() {
            val response = mockk<Response<Unit>>()
            every { response.isSuccessful } returns false
            every { response.code() } returns errorCode
            every { response.message() } returns errorMessage
            coEvery { service.deletePost(id) } returns response

            runTest {
                val result = sut.deletePost(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.deletePost(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN network exception response THEN returns Error")
    inner class NetworkExceptionResponse {

        @Test
        @DisplayName("WHEN posts fetched")
        fun getPosts() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getPosts() } throws throwable

            runTest {
                val result = sut.getPosts()
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPosts() }
        }

        @Test
        @DisplayName("WHEN post fetched")
        fun getPost() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getPost(id) } throws throwable

            runTest {
                val result = sut.getPost(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }
            
            coVerify { service.getPost(id) }
        }

        @Test
        @DisplayName("WHEN post comments fetched")
        fun getPostComments() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.getPostComments(id) } throws throwable
            
            runTest { 
                val result = sut.getPostComments(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }
            
            coVerify { service.getPostComments(id) }
        }

        @Test
        @DisplayName("WHEN posts added")
        fun postPosts() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.postPosts(listOf(postData)) } throws throwable
            
            runTest { 
                val result = sut.postPosts(listOf(postModel))
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }
            
            coVerifyOrder { 
                postMapper.map(postModel)
                service.postPosts(listOf(postData))
            }
        }

        @Test
        @DisplayName("WHEN posts updated")
        fun putPost() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.putPost(id, postData) } throws throwable

            runTest {
                val result = sut.putPost(id, postModel)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.putPost(id, postData)
            }
        }

        @Test
        @DisplayName("WHEN posts deleted")
        fun deletePost() {
            val throwable = mockk<HttpException>()
            every { throwable.code() } returns errorCode
            every { throwable.message } returns errorMessage
            coEvery { service.deletePost(id) } throws throwable

            runTest {
                val result = sut.deletePost(id)
                if (result is Result.Error) {
                    assertEquals(errorCode, result.code)
                    assertEquals(errorMessage, result.message)
                } else throw TestNotWorkingProperlyException()
            }

            coVerify { service.deletePost(id) }
        }
    }

    @Nested
    @DisplayName("GIVEN exception response THEN returns Exception")
    inner class ExceptionResponse {

        @Test
        @DisplayName("WHEN posts fetched")
        fun getPosts() {
            val throwable = mockk<Exception>()
            coEvery { service.getPosts() } throws throwable

            runTest {
                val result = sut.getPosts()
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPosts() }
        }

        @Test
        @DisplayName("WHEN post fetched")
        fun getPost() {
            val throwable = mockk<Exception>()
            coEvery { service.getPost(id) } throws throwable

            runTest {
                val result = sut.getPost(id)
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPost(id) }
        }

        @Test
        @DisplayName("WHEN post comments fetched")
        fun getPostComments() {
            val throwable = mockk<Exception>()
            coEvery { service.getPostComments(id) } throws throwable

            runTest {
                val result = sut.getPostComments(id)
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.getPostComments(id) }
        }

        @Test
        @DisplayName("WHEN posts added")
        fun postPosts() {
            val throwable = mockk<Exception>()
            coEvery { service.postPosts(listOf(postData)) } throws throwable

            runTest {
                val result = sut.postPosts(listOf(postModel))
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerifyOrder {
                postMapper.map(postModel)
                service.postPosts(listOf(postData))
            }
        }

        @Test
        @DisplayName("WHEN posts updated")
        fun putPost() {
            val throwable = mockk<Exception>()
            coEvery { service.putPost(id, postData) } throws throwable

            runTest {
                val result = sut.putPost(id, postModel)
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }
            coVerifyOrder {
                postMapper.map(postModel)
                service.putPost(id, postData)
            }
        }

        @Test
        @DisplayName("WHEN posts deleted")
        fun deletePost() {
            val throwable = mockk<Exception>()
            coEvery { service.deletePost(id) } throws throwable

            runTest {
                val result = sut.deletePost(id)
                if (result is Result.Exception) assertEquals(throwable, result.throwable)
                else throw TestNotWorkingProperlyException()
            }

            coVerify { service.deletePost(id) }
        }
    }
}
