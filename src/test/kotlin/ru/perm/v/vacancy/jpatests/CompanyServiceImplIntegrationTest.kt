package ru.perm.v.vacancy.jpatests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.repository.CompanyRepository
import ru.perm.v.vacancy.service.impl.CompanyServiceImpl
import ru.perm.v.vacancy.service.impl.VacancyServiceImpl
import kotlin.test.assertEquals

@DataJpaTest
class CompanyServiceImplIntegrationTest {
    @Autowired
    lateinit var companyRepository: CompanyRepository

    @MockBean
    lateinit var vacancyService: VacancyServiceImpl

    @Test
    fun getAll() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        val companies = service.getAll()

        assertEquals(4, companies.size)
        assertEquals(CompanyDto(-1, "-"), companies.get(0))
        assertEquals(CompanyDto(1, "COMPANY_1"), companies.get(1))
        assertEquals(CompanyDto(2, "COMPANY_2"), companies.get(2))
        assertEquals(CompanyDto(3, "3_COMPANY"), companies.get(3))
    }

    @Test
    fun getAllSortedByName() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        val companies = service.getAllSortedByField("name")

        assertEquals(4, companies.size)
        assertEquals(CompanyDto(-1, "-"), companies.get(0))
        assertEquals(CompanyDto(3, "3_COMPANY"), companies.get(1))
        assertEquals(CompanyDto(1, "COMPANY_1"), companies.get(2))
        assertEquals(CompanyDto(2, "COMPANY_2"), companies.get(3))
    }

    @Test
    fun getAllSortedByN() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        val companies = service.getAllSortedByField("n")

        assertEquals(4, companies.size)
        assertEquals(CompanyDto(-1, "-"), companies.get(0))
        assertEquals(CompanyDto(1, "COMPANY_1"), companies.get(1))
        assertEquals(CompanyDto(2, "COMPANY_2"), companies.get(2))
        assertEquals(CompanyDto(3, "3_COMPANY"), companies.get(3))
    }

    @Test
    fun create()  {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        val company = service.createCompany(CompanyDto(5, "COMPANY_4"))

        assertEquals("COMPANY_4", company.name)
    }

    @Test
    fun getNextN() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        assertEquals(5, service.getNextN())
    }
}