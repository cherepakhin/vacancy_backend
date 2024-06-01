package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.mapper.VacancyMapper
import ru.perm.v.vacancy.repository.VacancyRepository
import java.util.*

class VacancyServiceImplTest {
    @Test
    fun getByN() {
        val N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"
        val companyEntity = CompanyEntity(N_COMPANY, NAME_COMPANY)
        val vacancyEntity = VacancyEntity(N, NAME_VACANCY, COMMENT, companyEntity)

        val repository = Mockito.mock(VacancyRepository::class.java)
        val companyService = Mockito.mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService, VacancyMapper)
        Mockito.`when`(repository.findById(N)).thenReturn(Optional.of(vacancyEntity))

        val vacancyDto = service.getByN(N)

        assertEquals(N, vacancyDto.n)
        assertEquals(NAME_VACANCY, vacancyDto.name)
        assertEquals(COMMENT, vacancyDto.comment)
        assertEquals(CompanyDto(N_COMPANY, NAME_COMPANY), vacancyDto.company)
    }

    @Test
    fun getNoFoundByN() {
        val N = 100L
        val repository = Mockito.mock(VacancyRepository::class.java)
        Mockito.`when`(repository.findById(N)).thenReturn(Optional.empty())
        val companyService = Mockito.mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService, VacancyMapper)

        val thrown = assertThrows<Exception> { service.getByN(N) }

        assertEquals("Vacancy with N=100 not found", thrown.message)
    }
    
    @Test
    fun getAll()  {
        val N_100 = 100L
        val NAME_VACANCY_100 = "vacancy_100"
        val COMMENT_100 = "comment_100"
        val N_COMPANY_100 = 1L
        val NAME_COMPANY_100 = "company_100"
        val companyEntity_100 = CompanyEntity(N_COMPANY_100, NAME_COMPANY_100)
        val vacancyEntity100 = VacancyEntity(N_100, NAME_VACANCY_100, COMMENT_100, companyEntity_100)

        val N_200 = 200L
        val NAME_VACANCY_200 = "vacancy_200"
        val COMMENT_200 = "comment_200"
        val N_COMPANY_200 = 1L
        val NAME_COMPANY_200 = "company_200"
        val companyEntity200 = CompanyEntity(N_COMPANY_200, NAME_COMPANY_200)
        val vacancyEntity200 = VacancyEntity(N_200, NAME_VACANCY_200, COMMENT_200, companyEntity200)

        val repository = Mockito.mock(VacancyRepository::class.java)
        Mockito.`when`(repository.findAll()).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = Mockito.mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService, VacancyMapper)
        val vacancyDtos  = service.getAll()

        assertEquals(2, vacancyDtos.size)
        assertEquals(VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100, CompanyDto(N_COMPANY_100, NAME_COMPANY_100)),
            vacancyDtos[0]
        )
    }

    @Test
    fun createWithNotExistCompany() {
        val N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"
        val companyEntity = CompanyEntity(N_COMPANY, NAME_COMPANY)
        val vacancyEntity = VacancyEntity(N, NAME_VACANCY, COMMENT, companyEntity)

        val repository = Mockito.mock(VacancyRepository::class.java)
        Mockito.`when`(repository.save(vacancyEntity)).thenReturn(vacancyEntity)

    }
}