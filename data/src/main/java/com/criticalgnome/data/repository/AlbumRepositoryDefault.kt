package com.criticalgnome.data.repository

import com.criticalgnome.data.entity.AlbumData
import com.criticalgnome.data.mapper.AlbumMapper
import com.criticalgnome.data.service.ApiHandler
import com.criticalgnome.data.service.JsonPlaceholderService
import com.criticalgnome.domain.entity.AlbumModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryDefault @Inject constructor(
    private val service: JsonPlaceholderService,
    private val mapper: AlbumMapper
) : AlbumRepository {

    override suspend fun getAlbums(): Result<List<AlbumModel>> {
        return ApiHandler.Builder<List<AlbumData>, List<AlbumModel>>()
            .setServiceExecutor { service.getAlbums() }
            .setMappingExecutor { data -> data.map { mapper.map(it) } }
            .handleApi()
    }

    override suspend fun getAlbum(id: Int): Result<AlbumModel> {
        return ApiHandler.Builder<AlbumData, AlbumModel>()
            .setServiceExecutor { service.getAlbum(id) }
            .setMappingExecutor { mapper.map(it) }
            .handleApi()
    }
}
