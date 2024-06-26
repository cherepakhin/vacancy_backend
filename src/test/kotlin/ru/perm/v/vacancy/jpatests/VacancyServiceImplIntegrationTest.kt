package ru.perm.v.vacancy.jpatests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.impl.CompanyService
import ru.perm.v.vacancy.service.impl.VacancyServiceImpl
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DataJpaTest
/**
 * Testing integration with VacancyRepository
 */
class VacancyServiceImplIntegrationTest {
    @Autowired
    lateinit var vacancyRepository: VacancyRepository

    // Почему @Mockbean? Значение берется из Spring context,
    // но можно замокать нектр. методы. Если не замоканы, то будут работать исходные методы.
    @MockBean
    lateinit var companyService: CompanyService

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
}