package com.example.blogs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.blogs.base.platform.ApplicationException
import com.example.blogs.base.platform.ErrorType
import com.example.blogs.base.platform.Result
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.ui.MainViewModel
import com.example.blogs.features.usecase.GetBlogsUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.*
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
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var getBlogsUseCase: GetBlogsUseCase

    @Mock
    private lateinit var loadingObserver: Observer<in Boolean>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = MainViewModel(getBlogsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun verifyLoadingChangesCorrectlyOnGetBlogs() {
        testCoroutineDispatcher.runBlockingTest {
            val loading = MutableLiveData<Boolean>()
            viewModel.loading.observeForever {
                loading.postValue(it.peekContent().loading)
            }
            loading.observeForever(loadingObserver)

            verify(loadingObserver).onChanged(false)
            viewModel.getBlogs()
            verify(loadingObserver).onChanged(true)
        }
    }

    @Test
    fun verifyDataAndErrorChangesCorrectlyOnGetBlogsSuccess() {
        testCoroutineDispatcher.runBlockingTest {
            whenever(getBlogsUseCase.invoke()).then {
                Result.Success(emptyList<Blog>())
            }

            var error = viewModel.error.value?.peekContent()?.exception?.errorMessage
            var blogs = viewModel.blogs.value?.peekContent()

            assertNull(error)
            assertNull(blogs)

            viewModel.getBlogs()

            error = viewModel.error.value?.peekContent()?.exception?.errorMessage
            blogs = viewModel.blogs.value?.peekContent()

            assertNull(error)
            assertNotNull(blogs)
            assertEquals(emptyList<Blog>(), blogs)
        }
    }

    @Test
    fun verifyDataAndErrorChangesCorrectlyOnGetBlogsFail() {
        testCoroutineDispatcher.runBlockingTest {
            val errorType = ErrorType.Unexpected
            val errorMessage = "something went wrong"
            whenever(getBlogsUseCase.invoke()).then {
                Result.Error(ApplicationException(type = errorType, errorMessage = errorMessage))
            }

            var error = viewModel.error.value?.peekContent()?.exception?.errorMessage
            var blogs = viewModel.blogs.value?.peekContent()

            assertNull(error)
            assertNull(blogs)

            viewModel.getBlogs()

            error = viewModel.error.value?.peekContent()?.exception?.errorMessage
            blogs = viewModel.blogs.value?.peekContent()

            assertNotNull(error)
            assertEquals(errorMessage, error)
            assertNull(blogs)
        }
    }
}
