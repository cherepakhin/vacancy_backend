package ru.perm.v.vacancy.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.CompanyDtoForCreate
import ru.perm.v.vacancy.filter.CompanyExample
import ru.perm.v.vacancy.service.CompanyService

@WebMvcTest(controllers = [CompanyCtrl::class])
class CompanyServiceDeepSeekTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var companyService: CompanyService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `test echoMessage`() {
        val message = "Hello, World!"

        mockMvc.perform(get("/company/echo/{mes}", message))
            .andExpect(status().isOk)
            .andExpect(content().string(message))
    }

    @Test
    fun `test getByN`() {
        val companyN = 1L
        val companyDto = CompanyDto(companyN, "Test Company")
        `when`(companyService.getCompanyByN(companyN)).thenReturn(companyDto)

        mockMvc.perform(get("/company/{n}", companyN))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.n").value(companyN))
            .andExpect(jsonPath("$.name").value("Test Company"))
    }

    @Test
    fun `test getAll`() {
        val companies = listOf(CompanyDto(1L, "Company 1"), CompanyDto(2L, "Company 2"))
        `when`(companyService.getAllSortedByField("n")).thenReturn(companies)

        mockMvc.perform(get("/company/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].n").value(1L))
            .andExpect(jsonPath("$[0].name").value("Company 1"))
            .andExpect(jsonPath("$[1].n").value(2L))
            .andExpect(jsonPath("$[1].name").value("Company 2"))
    }

    @Test
    fun `test getAllSortByColumn with valid column`() {
        val companies = listOf(CompanyDto(1L, "Company 1"), CompanyDto(2L, "Company 2"))
        `when`(companyService.getAllSortedByField("name")).thenReturn(companies)

        mockMvc.perform(get("/company/sortByColumn/{column}", "name"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].n").value(1L))
            .andExpect(jsonPath("$[0].name").value("Company 1"))
            .andExpect(jsonPath("$[1].n").value(2L))
            .andExpect(jsonPath("$[1].name").value("Company 2"))
    }

    @Test
    fun `test getAllSortByColumn with invalid column`() {
        mockMvc.perform(get("/company/sortByColumn/invalidColumn"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `test getByExample`() {
        val companyExample = CompanyExample(name = "Test Company")
        val companies = listOf(CompanyDto(1L, "Test Company"))
        `when`(companyService.getByExample(companyExample)).thenReturn(companies)

        mockMvc.perform(
            put("/company/find/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyExample))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].n").value(1L))
            .andExpect(jsonPath("$[0].name").value("Test Company"))
    }

    @Test
    fun `test create`() {
        val companyDtoForCreate = CompanyDtoForCreate("New Company")
        val createdCompanyDto = CompanyDto(1L, "New Company")
        `when`(companyService.createCompany(companyDtoForCreate)).thenReturn(createdCompanyDto)

        mockMvc.perform(
            post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDtoForCreate))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.n").value(1L))
            .andExpect(jsonPath("$.name").value("New Company"))
    }

    @Test
    fun `test delete`() {
        val companyN = 1L
        `when`(companyService.deleteCompany(companyN)).thenReturn("Deleted")

        mockMvc.perform(delete("/company/{n}", companyN))
            .andExpect(status().isOk)
            .andExpect(content().string("Deleted"))
    }

    @Test
    fun `test update`() {
        val companyN = 1L
        val updatedCompanyDto = CompanyDto(companyN, "Updated Company")
        `when`(companyService.updateCompany(companyN, "Updated Company")).thenReturn(updatedCompanyDto)

        mockMvc.perform(
            post("/company/{n}", companyN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCompanyDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.n").value(companyN))
            .andExpect(jsonPath("$.name").value("Updated Company"))
    }
}