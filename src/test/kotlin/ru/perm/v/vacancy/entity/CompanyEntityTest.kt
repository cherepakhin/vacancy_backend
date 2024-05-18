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
}