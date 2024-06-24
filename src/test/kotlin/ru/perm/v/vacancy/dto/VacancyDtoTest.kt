package ru.perm.v.vacancy.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
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


    @Test
    fun equalsTest() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)

        assertEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByNameTest() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(10, "VACANCY_20", "COMMENT_10", companyDto)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByN_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(20, "VACANCY_10", "COMMENT_10", companyDto)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByNAME_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(10, "VACANCY_20", "COMMENT_10", companyDto)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByCOMMENT_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_20", companyDto)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByCOMPANY_Test() {
        val companyDto10 = CompanyDto(10, "COMPANY_10")
        val companyDto20 = CompanyDto(20, "COMPANY_20")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto20)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun hashCode_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)

        assertEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode())
    }

    @Test
    fun notEqHashCode_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto)
        val vacancyDto2 = VacancyDto(20, "VACANCY_10", "COMMENT_10", companyDto)

        assertNotEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode())
    }
}