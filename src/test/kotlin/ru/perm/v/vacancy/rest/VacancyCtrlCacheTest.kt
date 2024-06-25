package ru.perm.v.vacancy.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import ru.perm.v.vacancy.VacancyKotlinApplication
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.service.VacancyService
import ru.perm.v.vacancy.service.impl.CompanyService

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(VacancyKotlinApplication::class),
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VacancyCtrlCacheTest {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    private lateinit var vacancyService: VacancyService

    @MockBean
    private lateinit var companyService: CompanyService

    @Test
    fun vacancyGetAllCheckWorkCache() {

        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyDto = VacancyDto(1L, "title", "text", companyDto)
        Mockito.`when`(vacancyService.getAllSortedByField(VacancyColumn.NAME)).thenReturn(listOf(vacancyDto))

        val URL = "/vacancy/sortByColumn/name"
        // FIRST REQUEST
        testRestTemplate
            .getForEntity(URL, String::class.java)
        // SECOND REQUEST
        testRestTemplate
            .getForEntity(URL, String::class.java)
        // LAST REQUEST
        val result = testRestTemplate
            .getForEntity(URL, String::class.java)
        assertEquals(200, result.statusCode.value())
        val vacancies = ObjectMapper().readValue<List<VacancyDto>>(result.body)

        assertEquals(1, vacancies.size)
        // main test. 3 REST REQUESTS, but only 1 call VacancyService
        Mockito.verify(vacancyService, Mockito.times(1)).getAllSortedByField(VacancyColumn.NAME)
    }
}