package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.CompanyData
import com.criticalgnome.domain.entity.CompanyModel

interface CompanyMapper {
    fun map(companyData: CompanyData): CompanyModel
    fun map(companyModel: CompanyModel): CompanyData
}
