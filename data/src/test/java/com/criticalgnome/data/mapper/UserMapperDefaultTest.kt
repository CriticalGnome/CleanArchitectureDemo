package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AddressData
import com.criticalgnome.data.entity.CompanyData
import com.criticalgnome.data.entity.UserData
import com.criticalgnome.domain.entity.AddressModel
import com.criticalgnome.domain.entity.CompanyModel
import com.criticalgnome.domain.entity.UserModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserMapperDefaultTest {

    private val addressMapper = mockk<AddressMapper>()
    private val companyMapper = mockk<CompanyMapper>()
    private lateinit var sut: UserMapper

    private val addressData = mockk<AddressData>()
    private val addressModel = mockk<AddressModel>()
    private val companyData = mockk<CompanyData>()
    private val companyModel = mockk<CompanyModel>()

    private val data = UserData(
        id = 1,
        name = "name",
        username = "username",
        email = "email",
        address = addressData,
        phone = "phone",
        website = "website",
        company = companyData
    )
    private val model = UserModel(
        id = 1,
        name = "name",
        username = "username",
        email = "email",
        address = addressModel,
        phone = "phone",
        website = "website",
        company = companyModel
    )

    @BeforeEach
    fun setUp() {
        sut = UserMapperDefault(addressMapper, companyMapper)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(addressMapper, companyMapper)
    }

    @Test
    fun mapDataToModel() {
        every { addressMapper.map(addressData) } returns addressModel
        every { companyMapper.map(companyData) } returns companyModel
        assertEquals(model, sut.map(data))
        verifyAll {
            addressMapper.map(addressData)
            companyMapper.map(companyData)
        }
    }

    @Test
    fun mapModelToData() {
        every { addressMapper.map(addressModel) } returns addressData
        every { companyMapper.map(companyModel) } returns companyData
        assertEquals(data, sut.map(model))
        verifyAll {
            addressMapper.map(addressModel)
            companyMapper.map(companyModel)
        }
    }
}
