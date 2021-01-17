package com.example.blogs.features.data.repository

import com.example.blogs.base.platform.Result
import com.example.blogs.features.domain.Blog

interface IBlogsRepository {

    suspend fun getBlogs(): Result<List<Blog>>
}
