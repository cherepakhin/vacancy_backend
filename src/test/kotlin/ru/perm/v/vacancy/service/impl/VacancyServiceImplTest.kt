package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.*
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

        val repository = Mockito.mock(VacancyRepository::class.java)
        val companyService = Mockito.mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService, VacancyMapper)
        `when`(companyService.getCompanyByN(N_COMPANY)).thenThrow(Exception::class.java)

        val thrown  = assertThrows<Exception>  {
            service.create(VacancyDto(N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY)))
        }

        assertEquals("Company with N=1 not found", thrown.message)
    }

    @Test
    fun createWithExistCompany() {
        val N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val repository = Mockito.mock(VacancyRepository::class.java)
        val companyService = Mockito.mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService, VacancyMapper)
        `when`(companyService.getCompanyByN(N_COMPANY)).thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        `when`(repository.save(VacancyEntity(N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY))))
            .thenReturn(VacancyEntity(N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY)))

        val createdVacancyDto = service.create(
            VacancyDto(N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY))
        )

        assertEquals(
            VacancyDto(N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY)), createdVacancyDto)
    }

    @Test
    fun updateForNotExistVacancy()  {
        val VACANCY_N = 100L

        val mockVacancyRepository = Mockito.mock(VacancyRepository::class.java)
        val mockCompanyService = Mockito.mock(CompanyService::class.java)
        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, VacancyMapper)

        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val thrown   = assertThrows<Exception> {
            vacancyService.update(VACANCY_N,
                VacancyDto(VACANCY_N, "NAME_VACANCY", "COMMENT", CompanyDto(100L, "NAME_COMPANY")))
        }

        assertEquals("Vacancy with N=100 not found", thrown.message)
    }

    @Test
    fun updateWithExistVacancyAndExistCompany()  {
        val VACANCY_N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val mockVacancyRepository = Mockito.mock(VacancyRepository::class.java)
        val mockCompanyService = Mockito.mock(CompanyService::class.java)

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, VacancyMapper)
        val vacancyEntity  = VacancyEntity(VACANCY_N, NAME_VACANCY,
            COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY))
        `when`(mockVacancyRepository.findById(VACANCY_N))
            .thenReturn(Optional.of(vacancyEntity))
        `when`(mockCompanyService.getCompanyByN(N_COMPANY))
            .thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        `when`(mockVacancyRepository.save(vacancyEntity))
            .thenReturn(vacancyEntity)

        val updatedVacancyDto= vacancyService.update(
            VACANCY_N,
            VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY))
        )

        assertEquals(
            VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY)),
            updatedVacancyDto
        )

        verify(mockCompanyService, times(1)).getCompanyByN(N_COMPANY)
        verify(mockVacancyRepository, times(1)).save(vacancyEntity)
    }

    @Test
    fun delete() {
        val VACANCY_N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val mockVacancyRepository = Mockito.mock(VacancyRepository::class.java)
        val mockCompanyService = Mockito.mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(
            Optional.of(
                VacancyEntity(VACANCY_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY))
            )
        )

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, VacancyMapper)

        val result = vacancyService.delete(VACANCY_N)

        verify(mockVacancyRepository, times(1)).deleteById(VACANCY_N)
        assertEquals("OK", result)
    }

    @Test
    fun deleteOnNotExistVacancy()  {
        val VACANCY_N = 100L

        val mockVacancyRepository = Mockito.mock(VacancyRepository::class.java)
        val mockCompanyService = Mockito.mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, VacancyMapper)

        val thrown   = assertThrows<Exception> {
            vacancyService.delete(VACANCY_N)
        }

        verify(mockVacancyRepository, times(0)).deleteById(VACANCY_N)
        assertEquals("Vacancy with N=100 not found", thrown.message)
    }

    @Test
    fun deleteWithOtherException()  {
        val VACANCY_N = 100L

        val mockVacancyRepository = Mockito.mock(VacancyRepository::class.java)
        val mockCompanyService = Mockito.mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, VacancyMapper)

        val excpt = assertThrows<Exception>  {
            vacancyService.delete(VACANCY_N)
        }

        assertEquals("Vacancy with N=100 not found", excpt.message)
    }

}