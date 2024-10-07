package ru.perm.v.vacancy.validators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.dto.CompanyDto

class ValidatorCompanyDtoTest {

    @Test
    fun validateOk() {
        val companyDto = CompanyDto(1L, "test1")

        assertDoesNotThrow {
            ValidatorCompanyDto.validate(companyDto)
        }
    }


    //TODO: fail on v.perm.ru
    @Disabled
    @Test
    fun checkValidateMessageFor_EmptyName() {
        val companyDto = CompanyDto(-1L, "")

        val excpt = assertThrows(Exception::class.java) {
            ValidatorCompanyDto.validate(companyDto)
        }
        assertEquals(
            "CompanyDto(n=-1, name='') has errors: размер должен находиться в диапазоне от 5 до 50\n",
            excpt.message
        )
    }
}