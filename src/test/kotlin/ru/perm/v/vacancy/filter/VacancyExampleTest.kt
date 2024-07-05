package ru.perm.v.vacancy.filter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class VacancyExampleTest {
    @Test
    fun onCreate_N_isNull() {
        val vacancyExample1 = VacancyExample()

        assertEquals(listOf<Long>(), vacancyExample1.nn)
    }

    @Test
    fun onCreate_Name_isNull() {
        val vacancyExample1 = VacancyExample()

        assertNull(vacancyExample1.name)
    }

    @Test
    fun constructorWithCompanyNullTest() {
        val nn = listOf(1L)
        val vacancyExample1 = VacancyExample(nn, "NAME", null)

        assertEquals(listOf(1L), vacancyExample1.nn)
        assertEquals("NAME", vacancyExample1.name)
        assertNull(vacancyExample1.companyExample)
    }

    @Test
    fun constructorTest() {
        val nn = listOf(1L)
        val vacancyExample1 = VacancyExample(nn, "NAME", CompanyExample(10L,"COMPANY_10"))

        assertEquals(listOf(1L), vacancyExample1.nn)
        assertEquals("NAME", vacancyExample1.name)
        assertEquals(CompanyExample(10L,"COMPANY_10"), vacancyExample1.companyExample)
    }
}