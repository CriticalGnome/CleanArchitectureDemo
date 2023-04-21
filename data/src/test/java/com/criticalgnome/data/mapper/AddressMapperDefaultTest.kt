package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AddressData
import com.criticalgnome.data.entity.GeoData
import com.criticalgnome.domain.entity.AddressModel
import com.criticalgnome.domain.entity.GeoModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddressMapperDefaultTest {

    private val geoMapper = mockk<GeoMapper>()
    private lateinit var sut: AddressMapper

    private val geoData = mockk<GeoData>()
    private val geoModel = mockk<GeoModel>()

    private val data = AddressData(
        street = "street",
        suite = "suite",
        city = "city",
        zipcode = "zipcode",
        geo = geoData
    )
    private val model = AddressModel(
        street = "street",
        suite = "suite",
        city = "city",
        zipcode = "zipcode",
        geo = geoModel
    )

    @BeforeEach
    fun setUp() {
        sut = AddressMapperDefault(geoMapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(geoMapper)
    }

    @Test
    fun mapDataToModel() {
        every { geoMapper.map(geoData) } returns geoModel
        assertEquals(model, sut.map(data))
        verify { geoMapper.map(geoData) }
    }

    @Test
    fun mapModelToData() {
        every { geoMapper.map(geoModel) } returns geoData
        assertEquals(data, sut.map(model))
        verify { geoMapper.map(geoModel) }
    }
}
