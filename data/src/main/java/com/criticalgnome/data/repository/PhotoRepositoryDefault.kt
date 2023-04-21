package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.PhotoData
import com.criticalgnome.data.mapper.PhotoMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.PhotoModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val mapper: PhotoMapper
) : PhotoRepository {

    override suspend fun getPhotos(): Result<List<PhotoModel>> {
        return ApiHandler.Builder<List<PhotoData>, List<PhotoModel>>()
            .setServiceExecutor { service.getPhotos() }
            .setMappingExecutor { data -> data.map { mapper.map(it) } }
            .handleApi()
    }

    override suspend fun getPhoto(id: Int): Result<PhotoModel> {
        return ApiHandler.Builder<PhotoData, PhotoModel>()
            .setServiceExecutor { service.getPhoto(id) }
            .setMappingExecutor { mapper.map(it) }
            .handleApi()
    }
}
