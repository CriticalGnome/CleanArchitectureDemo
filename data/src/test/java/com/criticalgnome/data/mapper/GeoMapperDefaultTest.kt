package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.GeoData
import com.criticalgnome.domain.entity.GeoModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GeoMapperDefaultTest {

    private val sut: GeoMapper = GeoMapperDefault()

    private val data = GeoData("lat", "lng")
    private val model = GeoModel("lat", "lng")

    @Test
    fun mapDataToModel() {
        assertEquals(model, sut.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, sut.map(model))
    }
}
