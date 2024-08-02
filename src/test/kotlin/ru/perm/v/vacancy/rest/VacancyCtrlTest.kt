package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.filter.CompanyExample
import ru.perm.v.vacancy.filter.VacancyExample
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
        val vacancyDtoForCreate = VacancyDtoForCreate("VACANCY_1", "COMMENT_1", COMPANY_N)
        val companyDto = CompanyDto(1L, "COMPANY_1")
        val vacancyCreated = VacancyDto(VACANCY_N, "VACANCY_1", "COMMENT_1", companyDto)

        `when`(mockVacancyService.create(vacancyDtoForCreate)).thenReturn(vacancyCreated)

        val createdVacancy = vacancyCtrl.create(vacancyDtoForCreate)

        assertEquals(VacancyDto(VACANCY_N, "VACANCY_1", "COMMENT_1", companyDto), createdVacancy)
    }

    @Test
    fun createForNotValidDto() {
        val COMPANY_N = 1L
        val vacancy1 = VacancyDtoForCreate("", "", COMPANY_N)

        val err = assertThrows<Exception> { vacancyCtrl.create(vacancy1) }

        assertEquals(
            "VacancyDto(name='', comment='', company_n=1) has errors: размер должен находиться в диапазоне от 5 до 50\n",
            err.message
        )
    }

    @Test
    fun getAllWithErrorSortColumn() {
        val FAKE_COLUMN = "FAKE_COLUMN"
        val err = assertThrows<Exception> { vacancyCtrl.getAllSortByColumn(FAKE_COLUMN) }

        assertEquals("Invalid sort column: FAKE_COLUMN", err.message)
    }

    @Test
    fun getAllWithSortColumnNAME() {
        val sort_column = "NAME"
        `when`(mockVacancyService.getAllSortedByField(VacancyColumn.NAME))
            .thenReturn(
                listOf(
                    VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1"))
                )
            )

        val receivedDTO = vacancyCtrl.getAllSortByColumn(sort_column)

        assertEquals(listOf(VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1"))), receivedDTO)
        verify(mockVacancyService, times(1)).getAllSortedByField(VacancyColumn.NAME)
    }

    @Test
    fun getAllWithSortColumnN() {
        val sort_column = "N"
        `when`(mockVacancyService.getAllSortedByField(VacancyColumn.N))
            .thenReturn(
                listOf(
                    VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1")),
                    VacancyDto(2L, "", "", CompanyDto(1L, "COMPANY_1"))
                )
            )

        val receivedDTO = vacancyCtrl.getAllSortByColumn(sort_column)

        assertEquals(
            listOf(
                VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1")),
                VacancyDto(2L, "", "", CompanyDto(1L, "COMPANY_1"))
            ), receivedDTO
        )
        verify(mockVacancyService, times(1)).getAllSortedByField(VacancyColumn.N)
    }

    @Test
    fun getAllWithSortColumnCOMPANY_N() {
        `when`(mockVacancyService.getAllSortedByField(VacancyColumn.COMPANY_N))
            .thenReturn(
                listOf(
                    VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1")),
                    VacancyDto(2L, "", "", CompanyDto(2L, "COMPANY_2"))
                )
            )

        val receivedDTO = vacancyCtrl.getAllSortByColumn("COMPANY_N")

        assertEquals(
            listOf(
                VacancyDto(1L, "", "", CompanyDto(1L, "COMPANY_1")),
                VacancyDto(2L, "", "", CompanyDto(2L, "COMPANY_2"))
            ), receivedDTO
        )
        verify(mockVacancyService, times(1)).getAllSortedByField(VacancyColumn.COMPANY_N)
    }

    @Test
    fun deleteOk() {
        val VACANCY_N = 1L
        `when`(mockVacancyService.delete(VACANCY_N)).thenReturn("OK")
        `when`(mockVacancyService.getByN(VACANCY_N))
            .thenReturn(VacancyDto(VACANCY_N, "", "", CompanyDto(1L, "")))

        val result = vacancyCtrl.delete(VACANCY_N.toString())

        assertEquals("OK", result)
    }

    @Test
    fun deleteNotExist() {
        val VACANCY_N = 1L
        `when`(mockVacancyService.getByN(VACANCY_N)).thenReturn(null)

        val excpt = assertThrows<Exception> { vacancyCtrl.delete(VACANCY_N.toString()) }

        assertEquals("Vacancy with N=1 not found", excpt.message)
    }

    @Test
    fun deleteWithExceptionNotfound() {
        val VACANCY_N = 1L
        `when`(mockVacancyService.getByN(VACANCY_N)).thenReturn(null)

        val excpt = assertThrows<Exception> { vacancyCtrl.delete(VACANCY_N.toString()) }

        assertEquals("Vacancy with N=1 not found", excpt.message)
    }

    @Test
    fun deleteWithOtherException() {
        val VACANCY_N = 1L
        `when`(mockVacancyService.getByN(VACANCY_N)).thenReturn(VacancyDto(VACANCY_N, "", "", CompanyDto(1L, "")))
        `when`(mockVacancyService.delete(VACANCY_N)).doAnswer({ throw Exception("ANY ERROR") })

        val err = vacancyCtrl.delete(VACANCY_N.toString())

        assertEquals("ANY ERROR", err)
    }

    @Test
    fun createForNotExistCompany() {
        val COMPANY_N = 1L
        val vacancyDTO = VacancyDtoForCreate("NAME_1245", "", COMPANY_N) // length name must be greater or equal to 5

        `when`(mockCompanyService.getCompanyByN(COMPANY_N)).thenThrow(Exception("NOT EXIST COMPANY"))

        val err = assertThrows<Exception> { vacancyCtrl.create(vacancyDTO) }

        assertEquals(
            "NOT EXIST COMPANY",
            err.message
        )
    }
    @Test
    fun update() {
        val VACANCY_N = 10L
        val COMPANY_N = 100L
        val companyDto = CompanyDto(COMPANY_N, "COMPANY_100")
        val vacancyDTO = VacancyDto(VACANCY_N, "VACANCY_1", "", companyDto)

        `when`(mockVacancyService.update(VACANCY_N, vacancyDTO)).thenReturn(vacancyDTO)

        val updatedVacancy = vacancyCtrl.update(VACANCY_N, vacancyDTO)

        assertEquals(vacancyDTO, updatedVacancy)
    }
    @Test
    fun getByExampleForNN() {
        val VACANCY_N_10 = 10L
        val VACANCY_N_20 = 20L
        val vacancyExample = VacancyExample(listOf(VACANCY_N_10, VACANCY_N_20),"", CompanyExample())

        val COMPANY_N = 100L
        val companyDto = CompanyDto(COMPANY_N, "COMPANY_100")

        val vacancyDTO10 = VacancyDto(VACANCY_N_10, "VACANCY_10", "", companyDto)
        val vacancyDTO20 = VacancyDto(VACANCY_N_20, "VACANCY_20", "", companyDto)
        `when`(mockVacancyService.getByExample(vacancyExample)).thenReturn(listOf(vacancyDTO10, vacancyDTO20))

        val vacancies = vacancyCtrl.getByExample(vacancyExample)

        assertEquals(listOf(vacancyDTO10, vacancyDTO20), vacancies)
        verify(mockVacancyService, times(1)).getByExample(vacancyExample)
    }
}