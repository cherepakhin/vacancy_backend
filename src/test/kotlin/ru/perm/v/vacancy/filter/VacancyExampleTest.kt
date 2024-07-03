package ru.perm.v.vacancy.filter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class VacancyExampleTest {
    @Test
    fun onCreate_N_isNull() {
        val vacancyExample1 = VacancyExample()

        assertNull(vacancyExample1.n)
    }

    @Test
    fun onCreate_Name_isNull() {
        val vacancyExample1 = VacancyExample()

        assertNull(vacancyExample1.name)
    }

    @Test
    fun constructorWithCompanyNullTest() {
        val vacancyExample1 = VacancyExample(1L, "NAME", null)

        assertEquals(1L, vacancyExample1.n)
        assertEquals("NAME", vacancyExample1.name)
        assertNull(vacancyExample1.companyExample)
    }

    @Test
    fun constructorTest() {
        val vacancyExample1 = VacancyExample(1L, "NAME", CompanyExample(10L,"COMPANY_10"))

        assertEquals(1L, vacancyExample1.n)
        assertEquals("NAME", vacancyExample1.name)
        assertEquals(CompanyExample(10L,"COMPANY_10"), vacancyExample1.companyExample)
    }
}