package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CompanyData
import com.criticalgnome.domain.entity.CompanyModel
import javax.inject.Inject

class CompanyMapperDefault @Inject constructor() : CompanyMapper {

    override fun map(companyData: CompanyData): CompanyModel {
        return CompanyModel(
            name = companyData.name,
            catchPhrase = companyData.catchPhrase,
            bs = companyData.bs
        )
    }

    override fun map(companyModel: CompanyModel): CompanyData {
        return CompanyData(
            name = companyModel.name,
            catchPhrase = companyModel.catchPhrase,
            bs = companyModel.bs
        )
    }
}
