package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CompanyData
import com.criticalgnome.domain.entity.CompanyModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class CompanyMapperDefaultTest {

    private val sut: CompanyMapper = CompanyMapperDefault()

    private val data = CompanyData("name", "catchPhrase", "bs")
    private val model = CompanyModel("name", "catchPhrase", "bs")

    @Test
    fun mapDataToModel() {
        assertEquals(model, sut.map(data))
    }

    @Test
    fun mapModelToData() {
        assertEquals(data, sut.map(model))
    }
}
