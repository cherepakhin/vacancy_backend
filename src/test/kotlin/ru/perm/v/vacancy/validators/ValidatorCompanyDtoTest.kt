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


    @Test
    fun checkValidateMessageFor_EmptyName() {
        val companyDto = CompanyDto(1L, "")

        val excpt = assertThrows(Exception::class.java) {
            ValidatorCompanyDto.validate(companyDto)
        }
        // размер должен находиться в диапазоне от 5 до 50
        assertEquals(
            "CompanyDto(n=1, name='') has errors: размер должен находиться в диапазоне от 5 до 50\n",
            excpt.message
        )
    }

    @Test
    fun checkValidateMessageFor_n_0() {
        val companyDto = CompanyDto(0L, "NAME_NAME")

        val excpt = assertThrows(Exception::class.java) {
            ValidatorCompanyDto.validate(companyDto)
        }
        assertEquals(
            "CompanyDto(n=0, name='NAME_NAME') has errors: должно быть не меньше 1\n",
            excpt.message
        )
    }
}