package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.entity.TodoModel

interface TodoRepository {
    suspend fun getTodos(): Result<List<TodoModel>>
    suspend fun getTodo(id: Int): Result<TodoModel>
}
