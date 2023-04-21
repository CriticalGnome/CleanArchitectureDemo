package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.entity.UserModel

interface UserRepository {
    suspend fun getUsers(): Result<List<UserModel>>
    suspend fun getUser(id: Int): Result<UserModel>
}
