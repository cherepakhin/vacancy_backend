package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.impl.CompanyService

/**
 * Generated ChatGPT
 * https://openai.com/index/hello-gpt-4o/
 * https://chatgpt.com/c/7d936040-5426-42c4-ba24-8f45347a678e
 */
@ExtendWith(SpringExtension::class)
class CompanyCtrl_ChatGPT_Test {

    @Mock
    lateinit var companyService: CompanyService

    @InjectMocks
    lateinit var companyCtrl: CompanyCtrl

    @Test
    fun `getAll should return list of companies`() {
        // Arrange
        val company1 = CompanyDto(n = 1, name = "Company A")
        val company2 = CompanyDto(n = 2, name = "Company B")
        val companies = listOf(company1, company2)

        Mockito.`when`(companyService.getAll()).thenReturn(companies)

        // Act
        val result = companyCtrl.getAll()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Company A", result[0].name)
        assertEquals("Company B", result[1].name)
    }
}