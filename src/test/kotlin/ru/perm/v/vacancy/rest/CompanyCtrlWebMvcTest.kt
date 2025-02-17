package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.CompanyService
import ru.perm.v.vacancy.service.VacancyService

@WebMvcTest(controllers = [CompanyCtrl::class])
class CompanyCtrlWebMvcTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vacancyService: VacancyService

    @MockBean
    private lateinit var companyService: CompanyService

    @Test
    fun company_echo() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/company/echo/ECHO_MESSAGE")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("ECHO_MESSAGE"))
    }

    @Test
    fun getByN() {
        val COMPANY_N = 1L
        val companyDTO = CompanyDto(COMPANY_N, "COMPANY_1")
        `when`(companyService.getCompanyByN(COMPANY_N)).thenReturn(companyDTO)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/company/$COMPANY_N")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("{\"n\":1,\"name\":\"COMPANY_1\"}"))

    }
}