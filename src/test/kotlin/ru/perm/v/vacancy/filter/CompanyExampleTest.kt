package ru.perm.v.vacancy.filter

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CompanyExampleTest {

    @Test
    fun onCreate_N_isNull() {
        val companyExample1 = CompanyExample()

        assertNull( companyExample1.n)
    }

    @Test
    fun onCreate_Name_isNull() {
        val companyExample1 = CompanyExample()

        assertNull( companyExample1.name)
    }

    @Test
    fun constructorTest() {
        val companyExample1 = CompanyExample(1L, "NAME")

        assertEquals(1L, companyExample1.n)
        assertEquals("NAME", companyExample1.name)
    }
}