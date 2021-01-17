package com.example.blogs.features.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.blogs.base.platform.BaseViewModel
import com.example.blogs.base.utils.Event
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.usecase.GetBlogsUseCase

class MainViewModel @ViewModelInject constructor(
        private val getBlogsUseCase: GetBlogsUseCase
) : BaseViewModel() {

    val blogs = MutableLiveData<Event<List<Blog>>>()

    fun getBlogs() {
        wrapBlockingOperation {
            val result = getBlogsUseCase.invoke()
            handleResult(result) {
                blogs.postValue(Event(it.data))
            }
        }
    }
}
