package ru.perm.v.vacancy.jpatests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Sort
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.QCompanyEntity
import ru.perm.v.vacancy.filter.CompanyExample
import ru.perm.v.vacancy.repository.CompanyRepository
import ru.perm.v.vacancy.service.impl.CompanyServiceImpl
import ru.perm.v.vacancy.service.impl.VacancyServiceImpl
import kotlin.test.assertEquals

@DataJpaTest
/**
 * Testing integration with CompanyRepository
 */
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
    fun create() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        val company = service.createCompany(CompanyDto(5, "COMPANY_4"))

        assertEquals("COMPANY_4", company.name)
    }

    @Test
    fun getNextN() {
        val service = CompanyServiceImpl(companyRepository, vacancyService)

        assertEquals(4, service.getNextN())
    }

    @Test
    fun getByExampleEq() {
        val qbe: QCompanyEntity = QCompanyEntity.companyEntity
        val preicate = qbe.name.eq("COMPANY_1")
        val service  = CompanyServiceImpl(companyRepository, vacancyService)

        val companies  = service.findAll(preicate)

        assertEquals(1, companies.size)
    }
    @Test
    fun getByExampleLikeIgnoreCase() {
        val qbe: QCompanyEntity = QCompanyEntity.companyEntity
        val preicate = qbe.name.likeIgnoreCase("%company%")
        val service  = CompanyServiceImpl(companyRepository, vacancyService)

        val companies  = service.findAll(preicate)

        assertEquals(3, companies.size)
    }
    @Test
    fun getByExampleNameIgnoreCaseAndSort() {
        val service  = CompanyServiceImpl(companyRepository, vacancyService)
        val example = CompanyExample()
        example.name = "COMPANY_1"
        val companies  = service.getByExampleAndSort(
            example, Sort.by(Sort.Order.desc("n")))

        assertEquals(1, companies.size)
        assertEquals(CompanyDto(1,"COMPANY_1"), companies.get(0))
    }
    @Test
    fun getByExampleNIgnoreCaseAndSort() {
        val service  = CompanyServiceImpl(companyRepository, vacancyService)
        val example = CompanyExample()
        example.n = 1L
        val companies  = service.getByExampleAndSort(
            example, Sort.by(Sort.Order.desc("n")))

        assertEquals(1, companies.size)
    }
}