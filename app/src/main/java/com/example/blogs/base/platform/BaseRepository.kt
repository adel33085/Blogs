package com.example.blogs.base.platform

import com.example.blogs.R
import com.example.blogs.base.utils.IConnectivityUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var connectivityUtils: IConnectivityUtils

    private val noInternetError = Result.Error(ApplicationException(
            type = ErrorType.Network.NoInternetConnection,
            errorMessageRes = R.string.error_no_internet_connection
    ))

    private val unexpectedError = Result.Error(ApplicationException(
            type = ErrorType.Network.Unexpected,
            errorMessageRes = R.string.error_something_went_wrong
    ))

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                if (connectivityUtils.isNetworkConnected.not()) {
                    return@withContext noInternetError
                }
                val response = call()
                return@withContext handleResult(response)
            } catch (error: Throwable) {
                Timber.e(error)
                unexpectedError
            }
        }
    }

    private fun <T : Any> handleResult(response: Response<T>): Result<T> {
        return when (response.code()) {
            in 200..399 -> {
                Result.Success(response.body()!!)
            }
            401 -> {
                Result.Error(ApplicationException(type = ErrorType.Network.Unauthorized))
            }
            404 -> {
                Result.Error(ApplicationException(type = ErrorType.Network.ResourceNotFound))
            }
            else -> {
                Result.Error(ApplicationException(type = ErrorType.Network.Unexpected))
            }
        }
    }
}
