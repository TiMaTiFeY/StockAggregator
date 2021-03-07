package dev.timatifey.stockaggregator.data.network

import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

object ResponseHandler {

    fun <T: Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T: Any> handleException(exception: Exception, code: Int? = null): Resource<T> {
        return when(exception) {
            is HttpException -> Resource.error(exception.message(), null)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code), null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int?): String {
        return when(code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            else -> "Something went wrong [CODE: $code]"
        }
    }
}