package ru.perm.v.vacancy.validators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto

//TODO: do ENABLED
@Disabled
class ValidatorVacancyDtoTest {

    @Test
    fun validateOk() {
        val companyDto = CompanyDto(100L, "testCompany")
        val vacancyDto = VacancyDto(1L, "test1", "comment", companyDto)

        assertDoesNotThrow {
            ValidatorVacancyDto.validate(vacancyDto)
        }
    }

    //TODO: fail on v.perm.ru
    @Disabled
    @Test
    fun checkValidateMessageFor_EmptyName() {
        val companyDto = CompanyDto(100L, "testCompany")
        val vacancyDto = VacancyDto(1L, "", "comment", companyDto)

        val excpt = assertThrows(Exception::class.java) {
            ValidatorVacancyDto.validate(vacancyDto)
        }

        assertEquals(
            "VacancyDto(n=1, name='', comment='comment', company=CompanyDto(n=100, name='testCompany')) has errors: размер должен находиться в диапазоне от 5 до 50\n",
            excpt.message
        )
    }
}