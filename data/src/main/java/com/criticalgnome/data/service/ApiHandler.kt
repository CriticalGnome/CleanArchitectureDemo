package com.criticalgnome.data.service

import com.criticalgnome.domain.entity.Result
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any, D: Any> handleApi(
    execute: suspend () -> Response<T>,
    mapping: (data: T) -> D
): Result<D> {
    return try {
        val response = execute()
        if (response.isSuccessful && response.body() != null) Result.Success(mapping(response.body() as T))
        else Result.Error(response.code(), response.message())
    } catch (e: Exception) {
        if (e is HttpException) Result.Error(e.code(), e.message)
        else Result.Exception(e)
    }
}
