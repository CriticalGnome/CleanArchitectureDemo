package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PhotoData
import com.criticalgnome.domain.entity.PhotoModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class PhotoMapperDefaultTest {

    private val sut = PhotoMapperDefault()
    private val data = PhotoData(1, 1, "title", "url1", "url2")
    private val model = PhotoModel(1, 1, "title", "url1", "url2")

    @Test
    fun mapDataToModel() {
        assertEquals(model, sut.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, sut.map(model))
    }
}
