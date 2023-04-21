package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.AlbumModel
import com.criticalgnome.domain.entity.Result

interface AlbumRepository {
    suspend fun getAlbums(): Result<List<AlbumModel>>
    suspend fun getAlbum(id: Int): Result<AlbumModel>
}
