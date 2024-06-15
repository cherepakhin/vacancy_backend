package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.perm.v.vacancy.dto.CompanyDto

class ValidatorCompanyDtoTest {

    @Test
    fun validateOk()  {
        val companyDto = CompanyDto(1L,  "test1")

        assertDoesNotThrow {
            ValidatorCompanyDto.validate(companyDto)
        }
    }

    @Test
    fun checkValidateMessageFor_EmptyName()  {
        val companyDto = CompanyDto(-1L,  "")

        val excpt = assertThrows(Exception::class.java){
            ValidatorCompanyDto.validate(companyDto)
        }
        assertEquals("CompanyDto(n=-1, name='') has errors: размер должен находиться в диапазоне от 5 до 50\n", excpt.message)
    }
}