package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PostData
import com.criticalgnome.domain.entity.PostModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class PostMapperDefaultTest {

    private val data = PostData(1, 1, "title", "body")
    private val model = PostModel(1, 1, "title", "body")
    private val mapper = PostMapperDefault()

    @Test
    fun mapDataToModel() {
        assertEquals(model, mapper.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, mapper.map(model))
    }
}
