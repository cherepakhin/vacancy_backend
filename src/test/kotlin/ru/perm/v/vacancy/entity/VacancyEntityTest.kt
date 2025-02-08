package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class VacancyEntityTest {

    @Test
    fun constructorTest() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity20 = ContactEntity()
        contactEntity20.n = 20L

        val vacancy = VacancyEntity(100L, "NAME", "COMMENT", companyEntity, contactEntity20)

        assertEquals(100L, vacancy.n)
        assertEquals("NAME", vacancy.name)
        assertEquals("COMMENT", vacancy.comment)
        assertEquals(companyEntity, vacancy.company)
    }
    @Test
    fun constructorWithN_Test() {
        val vacancy = VacancyEntity(100L)

        assertEquals(100L, vacancy.n)
        assertEquals("", vacancy.name)
        assertEquals("", vacancy.comment)
        assertNull(vacancy.company)
    }

    @Test
    fun equalstTest() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity, contactEntity10)

        assertEquals(vacancy1, vacancy2)
    }

    @Test
    fun equalstWithCompaniesTest() {
        val companyEntity10 = CompanyEntity()
        companyEntity10.n = 10L
        companyEntity10.name = "COMPANY"

        val companyEntity20 = CompanyEntity()
        companyEntity20.n = 10L
        companyEntity20.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity10, contactEntity10)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity20, contactEntity10)

        assertEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByN_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(102L, "NAME", "COMMENT", companyEntity, contactEntity10)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByName_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME1", "COMMENT", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME2", "COMMENT", companyEntity, contactEntity10)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByComment_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME", "COMMENT2", companyEntity, contactEntity10)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByCompany_Test() {
        val companyEntity1 = CompanyEntity()
        companyEntity1.n = 1L
        companyEntity1.name = "COMPANY1"

        val companyEntity2 = CompanyEntity()
        companyEntity2.n = 2L
        companyEntity2.name = "COMPANY2"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity1, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity2, contactEntity10)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun equalsByHashCode() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity = ContactEntity()
        contactEntity.n = 10L

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity, contactEntity)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity, contactEntity)

        assertEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_N() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(102L, "NAME", "COMMENT", companyEntity, contactEntity10)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_NAME() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME_2", "COMMENT", companyEntity, contactEntity10)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_COMMENT() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME_1", "COMMENT_2", companyEntity, contactEntity10)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_COMPANY() {
        val companyEntity10 = CompanyEntity()
        companyEntity10.n = 10L
        companyEntity10.name = "COMPANY"

        val companyEntity20 = CompanyEntity()
        companyEntity20.n = 20L
        companyEntity20.name = "COMPANY"

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity10, contactEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity20, contactEntity10)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }
}