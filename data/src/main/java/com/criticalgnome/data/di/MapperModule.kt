package com.criticalgnome.data.di

import com.criticalgnome.data.mapper.AddressMapper
import com.criticalgnome.data.mapper.AddressMapperDefault
import com.criticalgnome.data.mapper.CommentMapper
import com.criticalgnome.data.mapper.CommentMapperDefault
import com.criticalgnome.data.mapper.CompanyMapper
import com.criticalgnome.data.mapper.CompanyMapperDefault
import com.criticalgnome.data.mapper.GeoMapper
import com.criticalgnome.data.mapper.GeoMapperDefault
import com.criticalgnome.data.mapper.PostMapper
import com.criticalgnome.data.mapper.PostMapperDefault
import com.criticalgnome.data.mapper.UserMapper
import com.criticalgnome.data.mapper.UserMapperDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun providePostMapper(): PostMapper {
        return PostMapperDefault()
    }

    @Provides
    @Singleton
    fun provideCommentMapper(): CommentMapper {
        return CommentMapperDefault()
    }

    @Provides
    @Singleton
    fun provideAddressMapper(geoMapper: GeoMapper): AddressMapper {
        return AddressMapperDefault(geoMapper)
    }

    @Provides
    @Singleton
    fun provideCompanyMapper(): CompanyMapper {
        return CompanyMapperDefault()
    }

    @Provides
    @Singleton
    fun provideGeoMapper(): GeoMapper {
        return GeoMapperDefault()
    }

    @Provides
    @Singleton
    fun provideUserMapper(
        addressMapper: AddressMapper,
        companyMapper: CompanyMapper
    ): UserMapper {
        return UserMapperDefault(
            addressMapper,
            companyMapper
        )
    }
}
