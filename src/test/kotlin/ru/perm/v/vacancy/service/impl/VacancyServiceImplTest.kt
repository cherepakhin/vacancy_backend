package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.data.domain.Sort
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.ContactEntity
import ru.perm.v.vacancy.entity.QVacancyEntity
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.filter.VacancyExample
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.CompanyService
import ru.perm.v.vacancy.service.ContactService
import java.util.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertTrue

class VacancyServiceImplTest {
    @Test
    fun getByN() {
        val N = 100L
        val NAME_VACANCY = "vacancy"
        val COMMENT = "comment"
        val N_COMPANY = 1L
        val NAME_COMPANY = "company"
        val companyEntity = CompanyEntity(N_COMPANY, NAME_COMPANY)
        val contactEntity = ContactEntity()
        contactEntity.n = 10L
        val vacancyEntity = VacancyEntity(N, NAME_VACANCY, COMMENT, companyEntity, contactEntity)

        val repository = mock(VacancyRepository::class.java)
        val companyService = mock(CompanyService::class.java)
        val contactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, companyService, contactService)
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
        val contactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, companyService, contactService)

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
        val contactEntity_10 = ContactEntity()
        contactEntity_10.n = 10L
        val vacancyEntity100 = VacancyEntity(N_100, NAME_VACANCY_100, COMMENT_100,
            companyEntity_100, contactEntity_10)

        val N_200 = 200L
        val NAME_VACANCY_200 = "vacancy_200"
        val COMMENT_200 = "comment_200"
        val N_COMPANY_200 = 1L
        val NAME_COMPANY_200 = "company_200"
        val companyEntity200 = CompanyEntity(N_COMPANY_200, NAME_COMPANY_200)
        val contactEntity20 = ContactEntity()
        contactEntity20.n = 20L
        val vacancyEntity200 = VacancyEntity(N_200, NAME_VACANCY_200, COMMENT_200,
            companyEntity200, contactEntity20)

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("n"))).thenReturn(
            listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val contactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, companyService, contactService)
        val vacancyDtos = service.getAll()
        val contactDto10 = ContactDto()
        contactDto10.n = 10L
        val contactDto20 = ContactDto()
        contactDto20.n = 20L

        assertEquals(2, vacancyDtos.size)

        assertEquals(contactDto10.n, vacancyDtos[0].contact.n)
        assertEquals(contactDto10.name, vacancyDtos[0].contact.name)
        assertEquals(contactDto10.email, vacancyDtos[0].contact.email)
        assertEquals(contactDto10.phone, vacancyDtos[0].contact.phone)
        assertEquals(contactDto10.comment, vacancyDtos[0].contact.comment)
        assertTrue(contactDto10.hashCode() == vacancyDtos[0].contact.hashCode())

        assertEquals(contactDto10, vacancyDtos[0].contact)
        assertEquals(CompanyDto(N_COMPANY_100, NAME_COMPANY_100), vacancyDtos[0].company)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100,
                CompanyDto(N_COMPANY_100, NAME_COMPANY_100), contactDto10),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200,
                CompanyDto(N_COMPANY_200, NAME_COMPANY_200), contactDto20),
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
        val contactService = mock(ContactService::class.java)
        `when`(companyService.getCompanyByN(COMPANY_N)).thenThrow(Exception("NOT FOUND"))

        val service = VacancyServiceImpl(repository, companyService, contactService)

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
        val contactService = mock(ContactService::class.java)
        val vacancyService = VacancyServiceImpl(repository, companyService, contactService)
        `when`(companyService.getCompanyByN(N_COMPANY)).thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        val VACANCY_NEXT_N = 101L
        `when`(repository.getNextN()).thenReturn(VACANCY_NEXT_N)
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        `when`(
            repository.save(
                VacancyEntity(
                    VACANCY_NEXT_N, NAME_VACANCY, COMMENT,
                    CompanyEntity(N_COMPANY, NAME_COMPANY),
                    contactEntity10
                )
            )
        ).thenReturn(
            VacancyEntity(
                VACANCY_NEXT_N, NAME_VACANCY, COMMENT,
                CompanyEntity(N_COMPANY, NAME_COMPANY),
                contactEntity10
            )
        )
        `when`(repository.getById(VACANCY_NEXT_N)).thenReturn(
            VacancyEntity(
                VACANCY_NEXT_N, NAME_VACANCY, COMMENT,
                CompanyEntity(N_COMPANY, NAME_COMPANY),
                contactEntity10
            )
        )

        val createdVacancyDto = vacancyService.create(
            VacancyDtoForCreate(NAME_VACANCY, COMMENT, N_COMPANY)
        )

        val contactDto10 = ContactDto()
        contactDto10.n = 10
        assertEquals(
            VacancyDto(VACANCY_NEXT_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY), contactDto10), createdVacancyDto
        )
    }

    @Test
    fun updateForNotExistVacancy() {
        val VACANCY_N = 100L

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        val contactService = mock(ContactService::class.java)
        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, contactService)

        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val contactDto10 = ContactDto()
        contactDto10.n = 10

        val thrown = assertThrows<Exception> {
            vacancyService.update(
                VACANCY_N, VacancyDto(VACANCY_N, "NAME_VACANCY", "COMMENT", CompanyDto(100L, "NAME_COMPANY"), contactDto10)
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
        val contactService = mock(ContactService::class.java)

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, contactService)
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        val vacancyEntity = VacancyEntity(
            VACANCY_N, NAME_VACANCY, COMMENT,
            CompanyEntity(N_COMPANY, NAME_COMPANY),
            contactEntity10
        )
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.of(vacancyEntity))
        `when`(mockCompanyService.getCompanyByN(N_COMPANY)).thenReturn(CompanyDto(N_COMPANY, NAME_COMPANY))
        `when`(mockVacancyRepository.save(vacancyEntity)).thenReturn(vacancyEntity)
        val contactDto10 = ContactDto()
        contactDto10.n = 10

        val updatedVacancyDto = vacancyService.update(
            VACANCY_N, VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY), contactDto10)
        )

        assertEquals(
            VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY), contactDto10), updatedVacancyDto
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
        val mockContactService = mock(ContactService::class.java)
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(
            Optional.of(
                VacancyEntity(VACANCY_N, NAME_VACANCY, COMMENT,
                    CompanyEntity(N_COMPANY, NAME_COMPANY),
                    contactEntity10
                )
            )
        )

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, mockContactService)
        `when`(mockCompanyService.getCompanyByN(N_COMPANY)).thenThrow(Exception("Company with N=1 not found"))
        val contactDto10 = ContactDto()
        contactDto10.n = 10

        val thrown = assertThrows<Exception> {
            vacancyService.update(
                VACANCY_N,
                VacancyDto(VACANCY_N, NAME_VACANCY, COMMENT, CompanyDto(N_COMPANY, NAME_COMPANY), contactDto10)
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
        val mockContactService = mock(ContactService::class.java)
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(
            Optional.of(
                VacancyEntity(VACANCY_N, NAME_VACANCY, COMMENT,
                    CompanyEntity(N_COMPANY, NAME_COMPANY),
                    contactEntity10
                )
            )
        )

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, mockContactService)

        val result = vacancyService.delete(VACANCY_N)

        verify(mockVacancyRepository, times(1)).deleteById(VACANCY_N)
        assertEquals("OK", result)
    }

    @Test
    fun deleteOnNotExistVacancy() {
        val VACANCY_N = 100L

        val mockVacancyRepository = mock(VacancyRepository::class.java)
        val mockCompanyService = mock(CompanyService::class.java)
        val mockContactService = mock(ContactService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, mockContactService)

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
        val mockContactService = mock(ContactService::class.java)
        `when`(mockVacancyRepository.findById(VACANCY_N)).thenReturn(Optional.empty())

        val vacancyService = VacancyServiceImpl(mockVacancyRepository, mockCompanyService, mockContactService)

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
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        val vacancyEntity100 = VacancyEntity(N_100, NAME_VACANCY_100, COMMENT_100,
            companyEntity_100, contactEntity10)

        val N_200 = 200L
        val NAME_VACANCY_200 = "vacancy_200"
        val COMMENT_200 = "comment_200"
        val N_COMPANY_200 = 1L
        val NAME_COMPANY_200 = "company_200"
        val companyEntity200 = CompanyEntity(N_COMPANY_200, NAME_COMPANY_200)
        val contactEntity20 = ContactEntity()
        contactEntity20.n = 20L
        val vacancyEntity200 = VacancyEntity(N_200, NAME_VACANCY_200, COMMENT_200,
            companyEntity200, contactEntity20)

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("n"))).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val mockCompanyService = mock(CompanyService::class.java)
        val mockContactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, mockCompanyService, mockContactService)

        val vacancyDtos = service.getAllSortedByField(VacancyColumn.N)
        val contactDto10 = ContactDto()
        contactDto10.n = 10
        val contactDto20 = ContactDto()
        contactDto20.n = 20

        assertEquals(2, vacancyDtos.size)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100,
                CompanyDto(N_COMPANY_100, NAME_COMPANY_100),
                contactDto10),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200,
                CompanyDto(N_COMPANY_200, NAME_COMPANY_200),
                contactDto20),
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
        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L
        val vacancyEntity100 = VacancyEntity(N_100, NAME_VACANCY_100, COMMENT_100,
            companyEntity_100, contactEntity10)

        val N_200 = 200L
        val NAME_VACANCY_200 = "vacancy_200"
        val COMMENT_200 = "comment_200"
        val N_COMPANY_200 = 1L
        val NAME_COMPANY_200 = "company_200"
        val companyEntity200 = CompanyEntity(N_COMPANY_200, NAME_COMPANY_200)
        val contactEntity20 = ContactEntity()
        contactEntity20.n = 20L
        val vacancyEntity200 = VacancyEntity(N_200, NAME_VACANCY_200, COMMENT_200,
            companyEntity200, contactEntity20)

        val repository = mock(VacancyRepository::class.java)
        `when`(repository.findAll(Sort.by("name"))).thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val contactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, companyService, contactService)

        val vacancyDtos = service.getAllSortedByField(VacancyColumn.NAME)
        val contactDto10 = ContactDto()
        contactDto10.n = 10
        val contactDto20 = ContactDto()
        contactDto20.n = 20

        assertEquals(2, vacancyDtos.size)
        assertEquals(
            VacancyDto(N_100, NAME_VACANCY_100, COMMENT_100,
                CompanyDto(N_COMPANY_100, NAME_COMPANY_100),
                contactDto10),
            vacancyDtos[0]
        )
        assertEquals(
            VacancyDto(N_200, NAME_VACANCY_200, COMMENT_200,
                CompanyDto(N_COMPANY_200, NAME_COMPANY_200),
                contactDto20),
            vacancyDtos[1]
        )
        verify(repository, times(1)).findAll(Sort.by("name"))
    }

    @Test
    fun listFields() {
        val props = VacancyEntity::class.declaredMemberProperties

        assertEquals(listOf("comment", "company", "contact", "n", "name"), props.map { it.name }.toList())
    }

    @Test
    fun getByExampleForVacancyId() {
        val vacancyExample = VacancyExample()
        vacancyExample.nn = listOf(100L, 200L)
        val repository = mock(VacancyRepository::class.java)
        val qVacancy = QVacancyEntity.vacancyEntity
        var predicate = qVacancy.n.goe(-1) // start query
        predicate = predicate.and(qVacancy.n.`in`(vacancyExample.nn))

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancyEntity100 = VacancyEntity(100L)
        vacancyEntity100.contact = contactEntity10
        val vacancyEntity200 = VacancyEntity(200L)
        vacancyEntity200.contact = contactEntity10

        val sort = Sort.by(Sort.Direction.ASC, "n")


        `when`(repository.findAll(predicate, sort))
            .thenReturn(listOf(vacancyEntity100, vacancyEntity200))

        val companyService = mock(CompanyService::class.java)
        val contactService = mock(ContactService::class.java)
        val service = VacancyServiceImpl(repository, companyService, contactService)

        val vacancies = service.getByExample(vacancyExample)
        val contactDto10 = ContactDto()
        contactDto10.n = 10

        assertEquals(2, vacancies.size)
        assertEquals(VacancyDto(100L, "", "", CompanyDto(-1L, ""), contactDto10), vacancies.get(0))
        assertEquals(VacancyDto(200L, "", "", CompanyDto(-1L, ""), contactDto10), vacancies.get(1))
    }

}