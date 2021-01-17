package com.example.blogs.base.platform

sealed class Result<out R> {

    data class Loading(val loading: Boolean) : Result<Nothing>()

    data class Success<T>(val data: T) : Result<T>()

    data class Error(val exception: ApplicationException) : Result<Nothing>()
}
