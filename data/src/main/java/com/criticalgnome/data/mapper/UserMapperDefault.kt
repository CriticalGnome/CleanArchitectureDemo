package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.UserData
import com.criticalgnome.domain.entity.UserModel
import javax.inject.Inject

class UserMapperDefault @Inject constructor(
    private val addressMapper: AddressMapper,
    private val companyMapper: CompanyMapper
) : UserMapper {

    override fun map(userData: UserData): UserModel {
        return UserModel(
            id = userData.id,
            name = userData.name,
            username = userData.username,
            email = userData.email,
            address = addressMapper.map(userData.address),
            phone = userData.phone,
            website = userData.website,
            company = companyMapper.map(userData.company)
        )
    }

    override fun map(userModel: UserModel): UserData {
        return UserData(
            id = userModel.id,
            name = userModel.name,
            username = userModel.username,
            email = userModel.email,
            address = addressMapper.map(userModel.address),
            phone = userModel.phone,
            website = userModel.website,
            company = companyMapper.map(userModel.company)
        )
    }
}
