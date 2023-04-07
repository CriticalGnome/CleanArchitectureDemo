package com.criticalgnome.domain.entity

sealed interface Result<T : Any> {
    class Success<T : Any>(val data: T) : Result<T>
    class Error<T : Any>(val code: Int = 0, val message: String?) : Result<T>
    class Exception<T : Any>(val throwable: Throwable) : Result<T>
}
