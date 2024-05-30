package ru.perm.v.vacancy.mapper

import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity

object CompanyMapper {
    fun toDto(companyEntity: CompanyEntity): CompanyDto {
        return CompanyDto(companyEntity.n,companyEntity.name);
    }

    fun toEntity(companyDto: CompanyDto): CompanyEntity {
        return CompanyEntity(companyDto.n,companyDto.name);
    }
}