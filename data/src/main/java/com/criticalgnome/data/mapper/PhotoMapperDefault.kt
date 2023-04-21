package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.PhotoData
import com.criticalgnome.domain.entity.PhotoModel
import javax.inject.Inject

class PhotoMapperDefault @Inject constructor() : PhotoMapper {

    override fun map(photoData: PhotoData): PhotoModel {
        return PhotoModel(
            albumId = photoData.albumId,
            id = photoData.id,
            title = photoData.title,
            url = photoData.url,
            thumbnailUrl = photoData.thumbnailUrl
        )
    }

    override fun map(photoModel: PhotoModel): PhotoData {
        return PhotoData(
            albumId = photoModel.albumId,
            id = photoModel.id,
            title = photoModel.title,
            url = photoModel.url,
            thumbnailUrl = photoModel.thumbnailUrl
        )
    }
}
