package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AddressData
import com.criticalgnome.domain.entity.AddressModel

interface AddressMapper {
    fun map(addressData: AddressData): AddressModel
    fun map(addressModel: AddressModel): AddressData
}
