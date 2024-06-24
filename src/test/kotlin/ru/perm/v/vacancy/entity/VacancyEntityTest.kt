package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class VacancyEntityTest {

    @Test
    fun constructorTest() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy = VacancyEntity(100L, "NAME", "COMMENT", companyEntity)

        assertEquals(100L, vacancy.n)
        assertEquals("NAME", vacancy.name)
        assertEquals("COMMENT", vacancy.comment)
        assertEquals(companyEntity, vacancy.companyEntity)
    }

    @Test
    fun equalstTest() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity)

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

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity10)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity20)

        assertEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByN_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(102L, "NAME", "COMMENT", companyEntity)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByName_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME1", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(101L, "NAME2", "COMMENT", companyEntity)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun notEqualstByComment_Test() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity)
        val vacancy2 = VacancyEntity(101L, "NAME", "COMMENT2", companyEntity)

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

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity1)
        val vacancy2 = VacancyEntity(101L, "NAME", "COMMENT1", companyEntity2)

        assertNotEquals(vacancy1, vacancy2)
    }

    @Test
    fun equalsByHashCode() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(100L, "NAME", "COMMENT", companyEntity)

        assertEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_N() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(102L, "NAME", "COMMENT", companyEntity)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_NAME() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT", companyEntity)
        val vacancy2 = VacancyEntity(101L, "NAME_2", "COMMENT", companyEntity)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }

    @Test
    fun notEqualsByHashCode_COMMENT() {
        val companyEntity = CompanyEntity()
        companyEntity.n = 10L
        companyEntity.name = "COMPANY"

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity)
        val vacancy2 = VacancyEntity(101L, "NAME_1", "COMMENT_2", companyEntity)

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

        val vacancy1 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity10)
        val vacancy2 = VacancyEntity(101L, "NAME_1", "COMMENT_1", companyEntity20)

        assertNotEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }
}