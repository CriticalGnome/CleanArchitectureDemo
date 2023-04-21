package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.TodoData
import com.criticalgnome.domain.entity.TodoModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class TodoMapperDefaultTest {

    private val sut = TodoMapperDefault()
    private val data = TodoData(1, 1, "title", true)
    private val model = TodoModel(1, 1, "title", true)

    @Test
    fun mapDataToModel() {
        assertEquals(model, sut.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, sut.map(model))
    }
}
