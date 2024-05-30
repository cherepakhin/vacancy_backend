package ru.perm.v.vacancy.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.VacancyEntity

class VacancyMapperTest {

    @Test
    fun toDto() {
        val N = 100L
        val NAME = "name"
        val COMMENT = "comment"

        val COMPANY_ENTITY = CompanyEntity(10L, "COMPANY")
        val entity = VacancyEntity(N, NAME, COMMENT, COMPANY_ENTITY)
        val dto = VacancyMapper.toDto(entity)

        val COMPANY_DTO = CompanyDto(10L, "COMPANY")
        assertEquals(VacancyDto(N, NAME, COMMENT, COMPANY_DTO), dto)
    }

    @Test
    fun toEntity() {
        val N = 100L
        val NAME = "name"
        val COMMENT = "comment"
        val COMPANY_DTO = CompanyDto(10L, "COMPANY")
        val vacancyDTO  = VacancyDto(N, NAME, COMMENT, COMPANY_DTO)
        val vacancyEntity  = VacancyMapper.toEntity(vacancyDTO)

        assertEquals(CompanyEntity(10L, "COMPANY"), vacancyEntity.companyEntity)
        assertEquals(VacancyEntity(N, NAME, COMMENT, CompanyEntity(10L, "COMPANY")), vacancyEntity)
    }
}