package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.TodoData
import com.criticalgnome.data.mapper.TodoMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.entity.TodoModel
import com.criticalgnome.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val mapper: TodoMapper
) : TodoRepository {

    override suspend fun getTodos(): Result<List<TodoModel>> {
        return ApiHandler.Builder<List<TodoData>, List<TodoModel>>()
            .setServiceExecutor { service.getTodos() }
            .setMappingExecutor { data -> data.map { mapper.map(it) } }
            .handleApi()
    }

    override suspend fun getTodo(id: Int): Result<TodoModel> {
        return ApiHandler.Builder<TodoData, TodoModel>()
            .setServiceExecutor { service.getTodo(id) }
            .setMappingExecutor { mapper.map(it) }
            .handleApi()
    }
}
