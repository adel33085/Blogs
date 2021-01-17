package com.example.blogs.features.data.api

import com.example.blogs.features.entity.BlogResponse
import retrofit2.Response
import retrofit2.http.GET

interface BlogsApi {

    @GET("blogs")
    suspend fun getBlogs(): Response<List<BlogResponse>>
}
