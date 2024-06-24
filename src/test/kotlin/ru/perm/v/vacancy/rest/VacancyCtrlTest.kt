package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.service.VacancyService
import ru.perm.v.vacancy.service.impl.CompanyService

@ExtendWith(MockitoExtension::class)
class VacancyCtrlTest {

    @Mock
    lateinit var mockVacancyService: VacancyService

    @Mock
    lateinit var mockCompanyService: CompanyService

    @InjectMocks
    private lateinit var vacancyCtrl: VacancyCtrl

    @Test
    fun getAll() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancy1 = VacancyDto(1L, "VACANCY_1", "COMMENT_1", companyDto)
        val vacancy2 = VacancyDto(2L, "VACANCY_2", "COMMENT_2", companyDto)
        val vacancies = listOf(
            vacancy1,
            vacancy2
        )
        `when`(mockVacancyService.getAll()).thenReturn(vacancies)

        val receivedVacancies = vacancyCtrl.getAll()

        assertEquals(vacancies, receivedVacancies)
    }

    @Test
    fun getByN() {
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancy1 = VacancyDto(1L, "VACANCY_1", "COMMENT_1", companyDto)
        `when`(mockVacancyService.getByN(1)).thenReturn(vacancy1)

        val receivedVacancy = vacancyCtrl.getByN(1)

        assertEquals(vacancy1, receivedVacancy)
    }

    @Test
    fun createForValidDto() {
        val COMPANY_N = 1L
        val VACANCY_N = 100L
        val vacancyDtoForCreate = VacancyDtoForCreate( "VACANCY_1", "COMMENT_1", COMPANY_N)
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyCreated = VacancyDto( VACANCY_N, "VACANCY_1", "COMMENT_1", companyDto)

        `when`(mockVacancyService.create(vacancyDtoForCreate)).thenReturn(vacancyCreated)

        val createdVacancy = vacancyCtrl.create(vacancyDtoForCreate)

        assertEquals(VacancyDto( VACANCY_N, "VACANCY_1", "COMMENT_1", companyDto), createdVacancy)
    }

    @Test
    fun createForNotValidDto() {
        val COMPANY_N  =  1L
        val vacancy1 = VacancyDtoForCreate("", "", COMPANY_N)

        val err = assertThrows<Exception> { vacancyCtrl.create(vacancy1) }

        assertEquals(
            "VacancyDto(name='', comment='', company_n=1) has errors: размер должен находиться в диапазоне от 5 до 50\n",
            err.message
        )
    }

    @Test
    fun getAllWithErrorSortColumn() {
        val FAKE_COLUMN  =  "FAKE_COLUMN"
        val err = assertThrows<Exception> { vacancyCtrl.getAllSortByColumn(FAKE_COLUMN) }

        assertEquals("Invalid sort column: FAKE_COLUMN", err.message)
    }

    @Test
    fun getAllWithSortColumnName() {
        val sort_column  =  "NAME"
        `when`(mockVacancyService.getAllSortedByField(VacancyColumn.NAME))
            .thenReturn(
                listOf(
                    VacancyDto(1L,  "",  "", CompanyDto(1L,  "COMPANY_1"))
                )
            )

        val receivedDTO= vacancyCtrl.getAllSortByColumn(sort_column)

        assertEquals(listOf(VacancyDto(1L,  "",  "", CompanyDto(1L,  "COMPANY_1"))), receivedDTO)
        verify(mockVacancyService, times(1)).getAllSortedByField(VacancyColumn.NAME)
    }
    //    @Test
//    fun createForNotExistCompany() {
//        val COMPANY_N = 1L
////        val companyDto = CompanyDto(COMPANY_N, "COMPANY_1")
//        val vacancy1 = VacancyDto(1L, "", "")
//
//        `when`(mockCompanyService.getCompanyByN(COMPANY_N)).thenThrow(Exception("NOT EXIST"))
//
//        val err = assertThrows<Exception> { vacancyCtrl.create(vacancy1) }
//
//        assertEquals(
//            "VacancyDto(n=1, name='', comment='', company=CompanyDto(n=1, name='COMPANY_1')) has errors: размер должен находиться в диапазоне от 5 до 50\n",
//            err.message
//        )
//    }

}