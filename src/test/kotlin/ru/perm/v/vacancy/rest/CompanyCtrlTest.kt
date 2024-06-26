package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.filter.CompanyExample
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
            CompanyDto(2L, "test2")
        )
        `when`(companyService.getAllSortedByField("n")).thenReturn(companyDTOs)
        val companies = companyCtrl.getAll()
        assertEquals(2, companies.size)
    }

    @Test
    fun getAllSorted() {
        val companyDTOs = listOf(
            CompanyDto(1L, "test1"),
            CompanyDto(2L, "test2")
        );
        val sortColumn = "n"
        `when`(companyService.getAllSortedByField(sortColumn)).thenReturn(companyDTOs)

        val companies = companyCtrl.getAll()

        assertEquals(2, companies.size)
    }

    @Test
    fun sortByColumn() {
        val companyDTOs = listOf(
            CompanyDto(1L, "test1"),
            CompanyDto(2L, "test2")
        );
        val SORT_COLUMN = "name"
        `when`(companyService.getAllSortedByField(SORT_COLUMN)).thenReturn(companyDTOs)

        val companies = companyCtrl.getAllSortByColumn(SORT_COLUMN)

        assertEquals(2, companies.size)
        assertEquals(1L, companies[0].n)
        assertEquals(2L, companies[1].n)

        assertEquals(listOf(1L, 2L), companies.map { it.n })

        verify(companyService).getAllSortedByField(SORT_COLUMN)
    }

    @Test
    fun sortByOtherColumn() {
        val companyDTOs = listOf(
            CompanyDto(100L, "COMPANY_100"),
            CompanyDto(200L, "COMPANY_200")
        );
        val SORT_COLUMN = "name"
        `when`(companyService.getAllSortedByField(SORT_COLUMN)).thenReturn(companyDTOs)

        val companies = companyCtrl.getAllSortByColumn(SORT_COLUMN)

        assertEquals(2, companies.size)
        assertEquals(100L, companies[0].n)
        assertEquals(200L, companies[1].n)

        assertEquals(listOf(100L, 200L), companies.map { it.n })

        verify(companyService).getAllSortedByField(SORT_COLUMN)
    }

    @Test
    fun sortByDefaultColumn() {
        val companyDTOs = listOf(
            CompanyDto(1L, "test1"),
            CompanyDto(2L, "test2")
        );

        `when`(companyService.getAllSortedByField("n")).thenReturn(companyDTOs)

        val companies = companyCtrl.getAllSortByColumn("")

        assertEquals(2, companies.size)
        assertEquals(1L, companies[0].n)
        assertEquals(2L, companies[1].n)

        val DEFAULT_SORT_COLUMN = "n"
        verify(companyService).getAllSortedByField(DEFAULT_SORT_COLUMN)
    }

    @Test
    fun sortByErrorColumn() {
        val err = assertThrows<Exception> { companyCtrl.getAllSortByColumn("1") }
        assertEquals("Invalid SORT column name", err.message)
    }

    @Test
    fun update() {
        val N = 1L
        val NEW_NAME = "new_name"
        val companyDto = CompanyDto(1L, NEW_NAME)
        val changedCompanyDto = CompanyDto(1L, NEW_NAME + "_from_service")
        `when`(companyService.updateCompany(N, NEW_NAME)).thenReturn(changedCompanyDto)

        val company = companyCtrl.update(N, companyDto)

        assertEquals(changedCompanyDto, company)

        verify(companyService, times(1)).updateCompany(N, NEW_NAME)
    }

    @Test
    fun delete() {
        val N = 100L
        `when`(companyService.deleteCompany(N)).thenReturn("OK")

        val resultStrng = companyCtrl.delete(N)

        assertEquals("OK", resultStrng)

        verify(companyService, times(1)).deleteCompany(N)
    }

    @Test
    fun create() {
        val N = 100L
        val NEW_NAME = "NEW_NAME"
        val companyDto = CompanyDto(N, NEW_NAME)
        `when`(companyService.createCompany(companyDto)).thenReturn(companyDto)

        val receivedDto = companyCtrl.create(companyDto)

        assertEquals(companyDto, receivedDto)
        verify(companyService, times(1)).createCompany(companyDto)
    }

    @Test
    fun getByExample() {
        val N = 100L
        val NEW_NAME = "NEW_NAME"

        val companyExample = CompanyExample(N, NEW_NAME)

        val companyDto = CompanyDto(N, NEW_NAME)
        `when`(companyService.getByExample(companyExample)).thenReturn(listOf(companyDto))

        val receivedDto = companyCtrl.getByExample(companyExample)

        assertEquals(1, receivedDto.size)
        assertEquals(companyDto, receivedDto.get(0))
        verify(companyService, times(1)).getByExample(companyExample)
    }
}