package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CommentData
import com.criticalgnome.domain.entity.CommentModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CommentMapperDefaultTest {

    private val data = CommentData(1, 1, "title", "email", "body")
    private val model = CommentModel(1, 1, "title", "email", "body")
    private val mapper = CommentMapperDefault()

    @Test
    fun mapDataToModel() {
        Assertions.assertEquals(model, mapper.map(data))
    }

    @Test
    fun mapModelToData() {
        Assertions.assertEquals(data, mapper.map(model))
    }
}
