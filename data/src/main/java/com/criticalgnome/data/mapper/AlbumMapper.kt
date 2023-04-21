package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.AlbumData
import com.criticalgnome.domain.entity.AlbumModel

interface AlbumMapper {
    fun map(albumData: AlbumData): AlbumModel
    fun map(albumModel: AlbumModel): AlbumData
}
