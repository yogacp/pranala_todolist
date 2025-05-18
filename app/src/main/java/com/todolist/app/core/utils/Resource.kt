package com.todolist.app.core.utils

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}

inline fun <T> Resource<T>.onSuccess(action: (T?) -> Unit) {
    if (this is Resource.Success) {
        action(this.data)
    }
}

inline fun <T> Resource<T>.onError(action: (String) -> Unit) {
    if (this is Resource.Error) {
        action(this.error?.message ?: "")
    }
}