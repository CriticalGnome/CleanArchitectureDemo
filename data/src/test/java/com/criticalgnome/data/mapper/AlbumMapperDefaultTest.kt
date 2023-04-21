package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AlbumData
import com.criticalgnome.domain.entity.AlbumModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class AlbumMapperDefaultTest {

    private val data = AlbumData(1, 1, "title")
    private val model = AlbumModel(1, 1, "title")
    private val sut = AlbumMapperDefault()

    @Test
    fun mapDataToModel() {
        assertEquals(model, sut.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, sut.map(model))
    }
}
