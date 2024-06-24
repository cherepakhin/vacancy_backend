package ru.perm.v.vacancy.consts

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VacancyColumnTest {

    @Test
    fun valueOfN() {
        assertEquals("n", VacancyColumn.N.value)
    }

    @Test
    fun valueOfNValue() {
        assertEquals("N", VacancyColumn.N.name)
    }

    @Test
    fun valueOfName() {
        assertEquals("NAME", VacancyColumn.NAME.name)
    }

    @Test
    fun nameValue() {
        assertEquals("name", VacancyColumn.NAME.value)
    }

    @Test
    fun companyValue() {
        assertEquals("company_n", VacancyColumn.COMPANY_N.value)
    }

    @Test
    fun ordinalOfN() {
        assertEquals(0, VacancyColumn.N.ordinal)
    }

    @Test
    fun ordinalOfName() {
        assertEquals(1, VacancyColumn.NAME.ordinal)
    }

    @Test
    fun ordinalOfCompany() {
        assertEquals(2, VacancyColumn.COMPANY_N.ordinal)
    }

    @Test
    fun existByString() {
        val column = "NAME"
        assertEquals("name", VacancyColumn.valueOf(column).value)
        assertEquals("name", VacancyColumn.values().filter { it.name == column }.get(0).value)
    }

    @Test
    fun notExistByString() {
        val column = "FAKE"
        assertThrows<IllegalArgumentException> { VacancyColumn.valueOf(column) }
    }

    @Test
    fun toUpperCase() {
        val column = "company_n"
        val upperColumn = column.uppercase()

        assertEquals("COMPANY_N", upperColumn)
        assertEquals("company_n", VacancyColumn.valueOf(upperColumn).value)
    }
}