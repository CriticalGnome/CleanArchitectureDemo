package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PhotoData
import com.criticalgnome.domain.entity.PhotoModel

interface PhotoMapper {
    fun map(photoData: PhotoData): PhotoModel
    fun map(photoModel: PhotoModel): PhotoData
}
