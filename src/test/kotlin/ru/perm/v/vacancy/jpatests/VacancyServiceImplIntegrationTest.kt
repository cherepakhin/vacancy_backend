package ru.perm.v.vacancy.jpatests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.filter.VacancyExample
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.CompanyService
import ru.perm.v.vacancy.service.impl.VacancyServiceImpl
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testing integration with VacancyRepository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
class VacancyServiceImplIntegrationTest {
    @Autowired
    lateinit var vacancyRepository: VacancyRepository

    // Почему @Mockbean? Значение берется из Spring context,
    // но можно замокать нектр. методы. Если не замоканы, то будут работать исходные методы.
    @MockBean
    lateinit var companyService: CompanyService

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @BeforeTest
    fun before() {
        logger.info("Start BEFORE EVERY test VacancyServiceImplIntegrationTest")
    }

    @Test
    fun getAllWithContains() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancies = service.getAll()

        assertEquals(4, vacancies.size)
        val companyDto1 = CompanyDto(1L, "COMPANY_1")
        val companyDto2 = CompanyDto(2L, "COMPANY_2")
        val companyDto3 = CompanyDto(3L, "3_COMPANY")

        assertTrue(
            vacancies.contains(
                VacancyDto(
                    1L,
                    "NAME_VACANCY_1_COMPANY_1",
                    "COMMENT_VACANCY_1_COMPANY_1",
                    companyDto1
                )
            )
        )
        assertTrue(
            vacancies.contains(
                VacancyDto(
                    2L,
                    "NAME_VACANCY_2_COMPANY_1",
                    "COMMENT_VACANCY_2_COMPANY_1",
                    companyDto1
                )
            )
        )
        assertTrue(
            vacancies.contains(
                VacancyDto(
                    3L,
                    "NAME_VACANCY_1_COMPANY_2",
                    "COMMENT_VACANCY_1_COMPANY_2",
                    companyDto2
                )
            )
        )
        println(vacancies)
        assertTrue(
            vacancies.contains(
                VacancyDto(
                    4L,
                    "NAME_VACANCY_1_COMPANY_3",
                    "COMMENT_VACANCY_1_COMPANY_3",
                    companyDto3
                )
            )
        )
    }

    @Test
    fun getAllWithEquals() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancies = service.getAll()

        assertEquals(4, vacancies.size)
        val companyDto1 = CompanyDto(1L, "COMPANY_1")
        val companyDto2 = CompanyDto(2L, "COMPANY_2")
        val companyDto3 = CompanyDto(3L, "3_COMPANY")

        assertEquals(
            VacancyDto(
                1L,
                "NAME_VACANCY_1_COMPANY_1",
                "COMMENT_VACANCY_1_COMPANY_1",
                companyDto1
            ), vacancies[0]
        )
        assertEquals(
            VacancyDto(
                2L,
                "NAME_VACANCY_2_COMPANY_1",
                "COMMENT_VACANCY_2_COMPANY_1",
                companyDto1
            ), vacancies[1]
        )
        assertEquals(
            VacancyDto(
                3L,
                "NAME_VACANCY_1_COMPANY_2",
                "COMMENT_VACANCY_1_COMPANY_2",
                companyDto2
            ), vacancies[2]
        )
        assertEquals(
            VacancyDto(
                4L,
                "NAME_VACANCY_1_COMPANY_3",
                "COMMENT_VACANCY_1_COMPANY_3",
                companyDto3
            ), vacancies[3]
        )
    }

    @Test
    fun getAllSortedByField() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancies = service.getAllSortedByField(VacancyColumn.COMPANY_N)

        assertEquals(4, vacancies.size)
    }

    @Test
    fun create() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)
        val vacancyDtoForCreate = VacancyDtoForCreate("NAME", "COMMENT", 1L)

        val vacancy = service.create(vacancyDtoForCreate)

        assertEquals("NAME", vacancy.name)
    }

    @Test
    fun getByExample_NAME_VACANCY() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancyExample = VacancyExample()
        vacancyExample.name = "VACANCY_1"

        val vacancies = service.getByExample(vacancyExample)

        // for debug
        println("----------------------")
        for (v in vacancies) {
            println(v)
        }
        println("----------------------")

        assertEquals(3, vacancies.size)
        assertEquals("NAME_VACANCY_1_COMPANY_1", vacancies.get(0).name)
        assertEquals("NAME_VACANCY_1_COMPANY_2", vacancies.get(1).name)
        assertEquals("NAME_VACANCY_1_COMPANY_3", vacancies.get(2).name)
    }

    @Test
    fun getByExample_N_VACANCY() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancyExample = VacancyExample()
        vacancyExample.nn = listOf(2L)

        val vacancies = service.getByExample(vacancyExample)

        assertEquals(1, vacancies.size)
        assertEquals(2L, vacancies[0].n)
    }

    @Test
    fun getByExample_N_and_NAME() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancyExample = VacancyExample()
        vacancyExample.nn = listOf(2L)
        vacancyExample.name = "VACANCY_2"

        val vacancies = service.getByExample(vacancyExample)

        assertEquals(1, vacancies.size)
        val companyDTO = CompanyDto(1L, "COMPANY_1")
        assertEquals(
            VacancyDto(
                2L, "NAME_VACANCY_2_COMPANY_1", "COMMENT_VACANCY_2_COMPANY_1",
                companyDTO
            ), vacancies[0]
        )
    }

    @Test
    fun deleteByN() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)

        val vacancyExample = VacancyExample()
        val N = 2L
        vacancyExample.nn = listOf(N)

        val vacanciesBeforeDelete = service.getByExample(vacancyExample)

        assertEquals(1, vacanciesBeforeDelete.size)
        val companyDTO = CompanyDto(1L, "COMPANY_1")
        // check, what vacancy exist, before delete
        assertEquals(
            VacancyDto(
                2L, "NAME_VACANCY_2_COMPANY_1", "COMMENT_VACANCY_2_COMPANY_1",
                companyDTO
            ), vacanciesBeforeDelete[0]
        )

        service.delete(N)

        val vacanciesAfterDelete = service.getByExample(vacancyExample)
        assertEquals(0, vacanciesAfterDelete.size)
    }

    @Test
    fun getNotExistVacancy() {
        val service = VacancyServiceImpl(vacancyRepository, companyService)
        val N = 10000L

        val err = assertThrows<Exception> { service.getByN(N) }

        assertEquals("Vacancy with N=10000 not found", err.message)
    }
}