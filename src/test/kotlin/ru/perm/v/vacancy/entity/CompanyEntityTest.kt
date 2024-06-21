package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CompanyEntityTest {
    @Test
    fun defaultConstructor() {
        val company = CompanyEntity()
        assertNotNull(company)
        assertEquals("", company.name)
        assertEquals(-1L, company.n)
    }

    @Test
    fun constructorWithName() {
        val company = CompanyEntity("test")

        assertNotNull(company)
        assertEquals("test", company.name)
        assertEquals(-1L, company.n)
    }

    @Test
    fun constructorWithIdAndName() {
        val company = CompanyEntity(100L, "test")

        assertNotNull(company)
        assertEquals("test", company.name)
        assertEquals(100L, company.n)
    }

    @Test
    fun equalsTest() {
        val company1 = CompanyEntity(100L, "test")
        val company2 = CompanyEntity(100L, "test")

        assertEquals(company1, company2)
    }

    @Test
    fun notEqualsByNameTest() {
        val company1 = CompanyEntity(100L, "-")
        val company2 = CompanyEntity(100L, "test")

        assertNotEquals(company1, company2)
    }

    @Test
    fun notEqualsByIdTest() {
        val company1 = CompanyEntity(1L, "test")
        val company2 = CompanyEntity(100L, "test")

        assertNotEquals(company1, company2)
    }
}