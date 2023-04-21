package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.TodoData
import com.criticalgnome.domain.entity.TodoModel
import javax.inject.Inject

class TodoMapperDefault @Inject constructor() : TodoMapper {

    override fun map(todoData: TodoData): TodoModel {
        return TodoModel(
            userId = todoData.userId,
            id = todoData.id,
            title = todoData.title,
            completed = todoData.completed
        )
    }

    override fun map(todoModel: TodoModel): TodoData {
        return TodoData(
            userId = todoModel.userId,
            id = todoModel.id,
            title = todoModel.title,
            completed = todoModel.completed
        )
    }
}
