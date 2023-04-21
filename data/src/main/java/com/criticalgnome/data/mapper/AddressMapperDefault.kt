package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AddressData
import com.criticalgnome.domain.entity.AddressModel
import javax.inject.Inject

class AddressMapperDefault @Inject constructor(
    private val geoMapper: GeoMapper
) : AddressMapper {

    override fun map(addressData: AddressData): AddressModel {
        return AddressModel(
            street = addressData.street,
            suite = addressData.suite,
            city = addressData.city,
            zipcode = addressData.zipcode,
            geo = geoMapper.map(addressData.geo)
        )
    }

    override fun map(addressModel: AddressModel): AddressData {
        return AddressData(
            street = addressModel.street,
            suite = addressModel.suite,
            city = addressModel.city,
            zipcode = addressModel.zipcode,
            geo = geoMapper.map(addressModel.geo)
        )
    }
}
