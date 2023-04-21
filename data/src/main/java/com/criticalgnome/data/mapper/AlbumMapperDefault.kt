package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AlbumData
import com.criticalgnome.domain.entity.AlbumModel
import javax.inject.Inject

class AlbumMapperDefault @Inject constructor() : AlbumMapper {

    override fun map(albumData: AlbumData): AlbumModel {
        return AlbumModel(
            userId = albumData.userId,
            id = albumData.id,
            title = albumData.title
        )
    }

    override fun map(albumModel: AlbumModel): AlbumData {
        return AlbumData(
            userId = albumModel.userId,
            id = albumModel.id,
            title = albumModel.title
        )
    }
}
