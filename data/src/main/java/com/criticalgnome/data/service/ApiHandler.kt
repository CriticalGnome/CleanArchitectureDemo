package com.criticalgnome.data.service

import com.criticalgnome.domain.entity.Result
import retrofit2.HttpException
import retrofit2.Response

class ApiHandler<T : Any, D : Any> private constructor(
    private val serviceExecute: suspend () -> Response<T>,
    private val mappingExecute: (data: T) -> D
) {
    suspend fun handleApi(): Result<D> {
        return try {
            val response = serviceExecute()
            if (response.isSuccessful && response.body() != null) Result.Success(mappingExecute(response.body() as T))
            else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            if (e is HttpException) Result.Error(e.code(), e.message)
            else Result.Exception(e)
        }
    }
    class Builder<T : Any, D : Any> {
        fun setServiceExecutor(execute: suspend () -> Response<T>): Builder2<T, D> {
            return Builder2(execute)
        }
        class Builder2<T : Any, D : Any>(private val serviceExecute: suspend () -> Response<T>) {
            fun setMappingExecutor(mappingExecute: (data: T) -> D): ApiHandler<T, D> {
                return ApiHandler(serviceExecute, mappingExecute)
            }
        }
    }
}
