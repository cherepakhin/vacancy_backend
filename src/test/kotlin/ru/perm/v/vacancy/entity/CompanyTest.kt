package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CompanyTest {
    @Test
    fun defaultConstructor() {
        val company = Company()
        assertNotNull(company)
        assertEquals("", company.name)
        assertEquals(-1L, company.n)
    }

    @Test
    fun constructorWithParams() {
        val company = Company(100L, "name")

        assertNotNull(company)
        assertEquals("name", company.name)
        assertEquals(100L, company.n)
    }

    @Test
    fun equals() {
        val company1 = Company(100L, "name")
        val company2 = Company(100L, "name")
        val company3 = Company(100L, "name1")
        val company4 = Company(101L, "name")

        assertTrue(company1 == company2)
        assertFalse(company1 == company3)
        assertFalse(company1 == company4)
    }
}