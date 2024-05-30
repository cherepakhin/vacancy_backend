package ru.perm.v.vacancy.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VacancyDtoTest {
    @Test
    fun create() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)

        assertEquals(companyDto, vacancyDto.company)
        assertEquals(10, vacancyDto.n)
        assertEquals("VACANCY_10", vacancyDto.name)
        assertEquals("COMMENT_10", vacancyDto.comment)
    }
}