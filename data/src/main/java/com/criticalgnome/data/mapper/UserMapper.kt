package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.UserData
import com.criticalgnome.domain.entity.UserModel

interface UserMapper {
    fun map(userData: UserData): UserModel
    fun map(userModel: UserModel): UserData
}
