package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.impl.CompanyService

@ExtendWith(MockitoExtension::class)
class CompanyCtrlTest {
    @Mock
    lateinit var companyService: CompanyService

    @InjectMocks
    lateinit var companyCtrl: CompanyCtrl

    @Test
    fun echo() {
        val message = companyCtrl.echoMessage("test")
        assertEquals("test", message)
    }

    @Test
    fun getAll() {
        val companyDTOs = listOf(
            CompanyDto(1L, "test1"),
            CompanyDto(2L, "test2"))
        `when`(companyService.getAll()).thenReturn(companyDTOs)
        val companies = companyCtrl.getAll()
        assertEquals(2, companies.size)
    }
}