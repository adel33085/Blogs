package com.example.blogs.features.usecase

import com.example.blogs.base.platform.Result
import com.example.blogs.features.data.repository.IBlogsRepository
import com.example.blogs.features.domain.Blog
import javax.inject.Inject

class GetBlogsUseCase @Inject constructor(private val repository: IBlogsRepository) {

    suspend operator fun invoke(): Result<List<Blog>> {
        return repository.getBlogs()
    }
}
