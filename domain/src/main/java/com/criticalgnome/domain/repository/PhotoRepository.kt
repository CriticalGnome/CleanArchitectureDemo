package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.PhotoModel
import com.criticalgnome.domain.entity.Result

interface PhotoRepository {
    suspend fun getPhotos(): Result<List<PhotoModel>>
    suspend fun getPhoto(id: Int): Result<PhotoModel>
}
