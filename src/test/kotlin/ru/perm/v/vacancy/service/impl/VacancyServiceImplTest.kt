package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.data.domain.Sort
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.QVacancyEntity
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.filter.VacancyExample
import ru.perm.v.vacancy.repository.VacancyRepository
import java.util.*
import kotlin.reflect.full.declaredMemberProperties

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

        val repository = mock(VacancyRepository::class.java)
        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)
        `when`(repository.findById(N)).thenReturn(Optional.of(vacancyEntity))

        val vacancyDto = service.getByN(N)

        assertEquals(N, vacancyDto.n)
        assertEquals(NAME_VACANCY, vacancyDto.name)
        assertEquals(COMMENT, vacancyDto.comment)
        assertEquals(CompanyDto(N_COMPANY, NAME_COMPANY), vacancyDto.company)
    }

    @Test
    fun getNoFoundByN() {
        val N = 100L
        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findById(N)).thenReturn(Optional.empty())
        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)

        val thrown = assertThrows<Exception> { service.getByN(N) }

        assertEquals("Vacancy with N=100 not found", thrown.message)
    }

    @Test
    fun getAll() {
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

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("n"))).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)
        val vacancyDtos = service.getAll()

        assertEquals(2, vacancyDtos.size)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100, CompanyDto(N_COMPANY_100, NAME_COMPANY_100)),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200, CompanyDto(N_COMPANY_200, NAME_COMPANY_200)),
            vacancyDtos[1]
        )
    }

    @Test
    fun createWithNotExistCompany() {
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val COMPANY_N = 1L

        val repository = mock(VacancyRepository::class.java)
        val companyService = mock(CompanyService::class.java)
        `when`(companyService.getCompanyByN(COMPANY_N)).thenThrow(Exception("NOT FOUND"))

        val service = VacancyServiceImpl(repository, companyService)

        val excpt = assertThrows<Exception> {
            service.create(VacancyDtoForCreate(NAME_VACANCY, COMMENT, COMPANY_N))
        }
        assertEquals("NOT FOUND", excpt.message)
    }

    @Test
    fun createWithExistCompany() {
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val repository = mock(VacancyRepository::class.java)
        val companyService = mock(CompanyService::class.java)
        val vacancyService = VacancyServiceImpl(repository, companyService)
        `when`(companyService.getCompanyByN(N_COMPANY)).thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        val VACANCY_NEXT_N = 101L
        `when`(repository.getNextN()).thenReturn(VACANCY_NEXT_N)
        `when`(
            repository.save(
                VacancyEntity(
                    VACANCY_NEXT_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY)
                )
            )
        ).thenReturn(
            VacancyEntity(
                VACANCY_NEXT_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY)
            )
        )
        `when`(repository.getById(VACANCY_NEXT_N)).thenReturn(
            VacancyEntity(
                VACANCY_NEXT_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY)
            )
        )

        val createdVacancyDto = vacancyService.create(
            VacancyDtoForCreate(NAME_VACANCY, COMMENT, N_COMPANY)
        )

        assertEquals(
            VacancyDto(VACANCY_NEXT_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY)), createdVacancyDto
        )
    }

    @Test
    fun updateForNotExistVacancy() {
        val VACANCY_N = 100L

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)

        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val thrown = assertThrows<Exception> {
            vacancyService.update(
                VACANCY_N, VacancyDto(VACANCY_N, "NAME_VACANCY", "COMMENT", CompanyDto(100L, "NAME_COMPANY"))
            )
        }

        assertEquals("Vacancy with N=100 not found", thrown.message)
    }

    @Test
    fun updateWithExistVacancyAndExistCompany() {
        val VACANCY_N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)
        val vacancyEntity = VacancyEntity(
            VACANCY_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY)
        )
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.of(vacancyEntity))
        `when`(mockCompanyService.getCompanyByN(N_COMPANY)).thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        `when`(mockVacancyRepository.save(vacancyEntity)).thenReturn(vacancyEntity)

        val updatedVacancyDto = vacancyService.update(
            VACANCY_N, VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY))
        )

        assertEquals(
            VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY)), updatedVacancyDto
        )

        verify(mockCompanyService, times(1)).getCompanyByN(N_COMPANY)
        verify(mockVacancyRepository, times(1)).save(vacancyEntity)
    }

    @Test
    fun updateWithExistVacancyAndNotExistCompany() {
        val VACANCY_N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(
            Optional.of(
                VacancyEntity(VACANCY_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY))
            )
        )

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)
        `when`(mockCompanyService.getCompanyByN(N_COMPANY)).thenThrow(Exception("Company with N=1 not found"))

        val thrown = assertThrows<Exception> {
            vacancyService.update(
                VACANCY_N, VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY))
            )
        }

        assertEquals("Company with N=1 not found", thrown.message) // message from ErrMessage.COMPANY_NOT_FOUND
    }

    @Test
    fun delete() {
        val VACANCY_N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(
            Optional.of(
                VacancyEntity(VACANCY_N, NAME_VACANCY, COMMENT, CompanyEntity(N_COMPANY, NAME_COMPANY))
            )
        )

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)

        val result = vacancyService.delete(VACANCY_N)

        verify(mockVacancyRepository, times(1)).deleteById(VACANCY_N)
        assertEquals("OK", result)
    }

    @Test
    fun deleteOnNotExistVacancy() {
        val VACANCY_N = 100L

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)

        val thrown = assertThrows<Exception> {
            vacancyService.delete(VACANCY_N)
        }

        verify(mockVacancyRepository, times(0)).deleteById(VACANCY_N)
        assertEquals("Vacancy with N=100 not found", thrown.message)
    }

    @Test
    fun deleteWithOtherException() {
        val VACANCY_N = 100L

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService)

        val excpt = assertThrows<Exception> {
            vacancyService.delete(VACANCY_N)
        }

        assertEquals("Vacancy with N=100 not found", excpt.message)
    }

    @Test
    fun getAllSortedByFieldN() {
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

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("n"))).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)

        val vacancyDtos = service.getAllSortedByField(VacancyColumn.N)

        assertEquals(2, vacancyDtos.size)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100, CompanyDto(N_COMPANY_100, NAME_COMPANY_100)),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200, CompanyDto(N_COMPANY_200, NAME_COMPANY_200)),
            vacancyDtos[1]
        )
    }

    @Test
    fun getAllSortedByFieldName() {
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

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("name"))).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)

        val vacancyDtos = service.getAllSortedByField(VacancyColumn.NAME)

        assertEquals(2, vacancyDtos.size)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100, CompanyDto(N_COMPANY_100, NAME_COMPANY_100)),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200, CompanyDto(N_COMPANY_200, NAME_COMPANY_200)),
            vacancyDtos[1]
        )
        verify(repository, times(1)).findAll(Sort.by("name"))
    }

    @Test
    fun listFields() {
        val props = VacancyEntity::class.declaredMemberProperties

        assertEquals(listOf("comment", "company", "n", "name"), props.map { it.name }.toList())
    }

    @Test
    fun getByExampleForVacancyId() {
        val vacancyExample = VacancyExample();
        vacancyExample.nn = listOf(100L, 200L)
        val repository = mock(VacancyRepository::class.java)
        val qVacancy = QVacancyEntity.vacancyEntity
        var predicate = qVacancy.n.goe(-1) // start query
        predicate = predicate.and(qVacancy.n.`in`(vacancyExample.nn))

        val vacancyEntity100 = VacancyEntity(100L)
        val vacancyEntity200 = VacancyEntity(200L)
        val sort= Sort.by(Sort.Direction.ASC, "n")

        `when`(repository.findAll(predicate, sort))
            .thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val service = VacancyServiceImpl(repository, companyService)

        val vacancies = service.getByExample(vacancyExample)

        assertEquals(2, vacancies.size)
        assertEquals(VacancyDto(100L,"","", CompanyDto(-1L, "")), vacancies.get(0))
        assertEquals(VacancyDto(200L,"","",CompanyDto(-1L, "")), vacancies.get(1))
    }

}