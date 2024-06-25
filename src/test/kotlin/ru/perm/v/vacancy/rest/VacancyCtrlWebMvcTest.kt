package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.service.VacancyService
import ru.perm.v.vacancy.service.impl.CompanyService


@WebMvcTest(controllers = [VacancyCtrl::class])
class VacancyCtrlWebMvcTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vacancyService: VacancyService

    @MockBean
    private lateinit var companyService: CompanyService

    @Test
    fun vacancy_echo() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyDto = VacancyDto(1L, "title", "text", companyDto)
        `when`(vacancyService.getByN(1L)).thenReturn(vacancyDto)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/vacancy/echo/ECHO_MESSAGE")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("ECHO_MESSAGE"))
    }

    @Test
    fun vacancyGetAll() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyDto1 = VacancyDto(1L, "title", "text", companyDto)
        val vacancyDto2 = VacancyDto(2L, "title", "text", companyDto)
        `when`(vacancyService.getAll()).thenReturn(listOf(vacancyDto1, vacancyDto2))

        mockMvc.get("/vacancy/") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("[{\"n\":1,\"name\":\"title\",\"comment\":\"text\",\"company\":{\"n\":1,\"name\":\"COMPANY_1\"}},{\"n\":2,\"name\":\"title\",\"comment\":\"text\",\"company\":{\"n\":1,\"name\":\"COMPANY_1\"}}]") }
        }
    }

    @Test
    fun getAllSortByColumnNAME() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyDto1 = VacancyDto(1L, "title", "text", companyDto)
        val vacancyDto2 = VacancyDto(2L, "title", "text", companyDto)
        `when`(vacancyService.getAllSortedByField(VacancyColumn.NAME)).thenReturn(listOf(vacancyDto1, vacancyDto2))

        mockMvc.get("/vacancy/sortByColumn/name") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("[{\"n\":1,\"name\":\"title\",\"comment\":\"text\",\"company\":{\"n\":1,\"name\":\"COMPANY_1\"}},{\"n\":2,\"name\":\"title\",\"comment\":\"text\",\"company\":{\"n\":1,\"name\":\"COMPANY_1\"}}]") }
        }
    }

    @Test
    fun vacancyGetByN() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyDto1 = VacancyDto(1L, "title", "text", companyDto)
        `when`(vacancyService.getByN(1L)).thenReturn(vacancyDto1)

        mockMvc.get("/vacancy/1") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("{\"n\":1,\"name\":\"title\",\"comment\":\"text\",\"company\":{\"n\":1,\"name\":\"COMPANY_1\"}}") }
        }
    }
}