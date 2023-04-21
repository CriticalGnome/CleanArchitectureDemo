package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.TodoData
import com.criticalgnome.domain.entity.TodoModel

interface TodoMapper {
    fun map(todoData: TodoData): TodoModel
    fun map(todoModel: TodoModel): TodoData
}
