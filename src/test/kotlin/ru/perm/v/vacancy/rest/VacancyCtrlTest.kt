package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.service.VacancyService

@ExtendWith(MockitoExtension::class)
class VacancyCtrlTest {

    @Mock
    lateinit var mockVacancyService: VacancyService

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

        val receivedVacancy  = vacancyCtrl.getByN(1)

        assertEquals(vacancy1, receivedVacancy)
    }
}