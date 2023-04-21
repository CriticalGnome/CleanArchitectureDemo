package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.GeoData
import com.criticalgnome.domain.entity.GeoModel
import javax.inject.Inject

class GeoMapperDefault @Inject constructor() : GeoMapper {

    override fun map(geoData: GeoData): GeoModel {
        return GeoModel(
            lat = geoData.lat,
            lng = geoData.lng
        )
    }

    override fun map(geoModel: GeoModel): GeoData {
        return GeoData(
            lat = geoModel.lat,
            lng = geoModel.lng
        )
    }
}
