package com.example.blogs.di

import com.example.blogs.features.data.repository.BlogsRepository
import com.example.blogs.features.data.repository.IBlogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindBlogsRepository(impl: BlogsRepository): IBlogsRepository
}
