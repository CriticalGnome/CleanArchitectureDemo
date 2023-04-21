package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.UserData
import com.criticalgnome.data.mapper.UserMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.entity.UserModel
import com.criticalgnome.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun getUsers(): Result<List<UserModel>> {
        return ApiHandler.Builder<List<UserData>, List<UserModel>>()
            .setServiceExecutor { service.getUsers() }
            .setMappingExecutor { data -> data.map { mapper.map(it) } }
            .handleApi()
    }

    override suspend fun getUser(id: Int): Result<UserModel> {
        return ApiHandler.Builder<UserData, UserModel>()
            .setServiceExecutor { service.getUser(id) }
            .setMappingExecutor { mapper.map(it) }
            .handleApi()
    }
}
