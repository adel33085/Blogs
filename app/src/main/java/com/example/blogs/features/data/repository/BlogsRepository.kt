package com.example.blogs.features.data.repository

import com.example.blogs.base.platform.ApplicationException
import com.example.blogs.base.platform.BaseRepository
import com.example.blogs.base.platform.ErrorType
import com.example.blogs.base.platform.Result
import com.example.blogs.features.data.api.BlogsApi
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.entity.toBlog
import javax.inject.Inject

class BlogsRepository @Inject constructor(
        private val blogsApi: BlogsApi
) : BaseRepository(), IBlogsRepository {

    override suspend fun getBlogs(): Result<List<Blog>> {
        val result = safeApiCall {
            blogsApi.getBlogs()
        }
        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.mapNotNull { it.toBlog() })
            }
            is Result.Error -> {
                result
            }
            else -> {
                Result.Error(ApplicationException(type = ErrorType.Unexpected))
            }
        }
    }
}
