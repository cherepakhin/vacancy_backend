package ru.perm.v.vacancy.mapper

import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.VacancyEntity

object VacancyMapper {

    fun toDto(vacancyEntity: VacancyEntity): VacancyDto {
        val companyEntity = vacancyEntity.company ?: CompanyEntity()
        return VacancyDto(vacancyEntity.n,
            vacancyEntity.name, vacancyEntity.comment, CompanyMapper.toDto(companyEntity));
    }

    fun toEntity(vacancyDto: VacancyDto): VacancyEntity {
        val vacancyEntity = VacancyEntity()
        vacancyEntity.n = vacancyDto.n
        vacancyEntity.name  = vacancyDto.name
        vacancyEntity.comment  = vacancyDto.comment
        vacancyEntity.company = CompanyMapper.toEntity(vacancyDto.company)
        return vacancyEntity;
    }
}