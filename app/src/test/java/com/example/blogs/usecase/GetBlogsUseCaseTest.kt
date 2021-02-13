package com.example.blogs.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.blogs.base.platform.ApplicationException
import com.example.blogs.base.platform.ErrorType
import com.example.blogs.base.platform.Result
import com.example.blogs.features.data.repository.IBlogsRepository
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.usecase.GetBlogsUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetBlogsUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var getBlogsUseCase: GetBlogsUseCase

    @Mock
    private lateinit var blogsRepository: IBlogsRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        getBlogsUseCase = GetBlogsUseCase(blogsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun verifyGettingDataWheneverSuccess() {
        testCoroutineDispatcher.runBlockingTest {
            whenever(blogsRepository.getBlogs()).then {
                Result.Success(emptyList<Blog>())
            }
            val result = (getBlogsUseCase.invoke() as Result.Success).data
            assertNotNull(result)
            assertEquals(emptyList<Blog>(), result)
        }
    }

    @Test
    fun verifyGettingExceptionWheneverFail() {
        testCoroutineDispatcher.runBlockingTest {
            val errorType = ErrorType.Unexpected
            val errorMessage = "something went wrong"
            whenever(blogsRepository.getBlogs()).then {
                Result.Error(ApplicationException(type = errorType, errorMessage = errorMessage))
            }
            val result = (getBlogsUseCase.invoke() as Result.Error).exception
            assertNotNull(result)
            assertEquals(errorMessage, result.errorMessage)
        }
    }
}
