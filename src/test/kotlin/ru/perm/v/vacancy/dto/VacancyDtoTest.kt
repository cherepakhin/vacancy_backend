package ru.perm.v.vacancy.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.entity.ContactEntity

class VacancyDtoTest {
    @Test
    fun create() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L

        val vacancyDto = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)

        assertEquals(companyDto, vacancyDto.company)
        assertEquals(10, vacancyDto.n)
        assertEquals("VACANCY_10", vacancyDto.name)
        assertEquals("COMMENT_10", vacancyDto.comment)
        assertEquals(10L, vacancyDto.contact.n)
    }


    @Test
    fun equalsTest() {
        val companyDto_1 = CompanyDto(10, "COMPANY_10")
        val companyDto_2 = CompanyDto(10, "COMPANY_10")
        val contactDto10_1 = ContactDto()
        contactDto10_1.n = 10L
        val contactDto10_2 = ContactDto()
        contactDto10_2.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto_1, contactDto10_1)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto_2, contactDto10_2)

        assertEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByNameTest() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_20", "COMMENT_10", companyDto, contactDto10)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByN_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(20, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByNAME_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_20", "COMMENT_10", companyDto, contactDto10)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByCOMMENT_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_20", companyDto, contactDto10)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun notEqualsByCOMPANY_Test() {
        val companyDto10 = CompanyDto(10, "COMPANY_10")
        val companyDto20 = CompanyDto(20, "COMPANY_20")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto10, contactDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto20, contactDto10)

        assertNotEquals(vacancyDto1, vacancyDto2)
    }

    @Test
    fun hashCode_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)

        assertEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode())
    }

    @Test
    fun notEqHashCode_Test() {
        val companyDto = CompanyDto(10, "COMPANY_10")
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val vacancyDto1 = VacancyDto(10, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)
        val vacancyDto2 = VacancyDto(20, "VACANCY_10", "COMMENT_10", companyDto, contactDto10)

        assertNotEquals(vacancyDto1.hashCode(), vacancyDto2.hashCode())
    }
}