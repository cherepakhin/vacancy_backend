package ru.perm.v.vacancy.consts

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class VacancyColumnsTest {

    @Test
    fun valueOfN() {
        assertEquals("n", VacancyColumns.N.value)
    }

    @Test
    fun valueOfNValue() {
        assertEquals("N", VacancyColumns.N.name)
    }

    @Test
    fun valueOfName() {
        assertEquals("NAME", VacancyColumns.NAME.name)
    }

    @Test
    fun nameValue() {
        assertEquals("name", VacancyColumns.NAME.value)
    }

    @Test
    fun companyValue() {
        assertEquals("company_n", VacancyColumns.COMPANY.value)
    }

    @Test
    fun ordinalOfN() {
        assertEquals(0, VacancyColumns.N.ordinal)
    }

    @Test
    fun ordinalOfName() {
        assertEquals(1, VacancyColumns.NAME.ordinal)
    }

    @Test
    fun ordinalOfCompany() {
        assertEquals(2, VacancyColumns.COMPANY.ordinal)
    }
}