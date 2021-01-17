package com.example.blogs.di

import com.example.blogs.features.data.api.BlogsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@InstallIn(ActivityComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideBlogsApi(retrofit: Retrofit): BlogsApi {
        return retrofit.create(BlogsApi::class.java)
    }
}
