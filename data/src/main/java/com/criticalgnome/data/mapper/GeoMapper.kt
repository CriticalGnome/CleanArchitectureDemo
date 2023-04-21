package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.GeoData
import com.criticalgnome.domain.entity.GeoModel

interface GeoMapper {
    fun map(geoData: GeoData): GeoModel
    fun map(geoModel: GeoModel): GeoData
}
