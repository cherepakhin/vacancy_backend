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
    fun valueOfNameValue() {
        assertEquals("name", VacancyColumns.NAME.value)
    }
}