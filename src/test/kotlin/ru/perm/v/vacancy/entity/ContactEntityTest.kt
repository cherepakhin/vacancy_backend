package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ContactEntityTest {
    @Test
    fun defaultConstructor() {
        val c = ContactEntity()

        assertNotNull(c)
        assertEquals(-1L, c.n)
        assertEquals("", c.name)
        assertEquals("", c.email)
        assertEquals("", c.phone)
        assertEquals("", c.comment)
    }

    @Test
    fun secondaryConstructor() {
        val NAME = "NAME"
        val EMAIL = "EMAIL"
        val PHONE = "PHONE"
        val COMMENT = "COMMENT"
        val COMPANY = CompanyEntity()

        val contact = ContactEntity(NAME, EMAIL, PHONE, COMMENT, COMPANY)

        assertNotNull(contact)
        assertNotNull(contact.n)
        assertEquals(NAME, contact.name)
        assertEquals(COMPANY, contact.companyEntity)
        assertEquals(EMAIL, contact.email)
        assertEquals(PHONE, contact.phone)
        assertEquals(COMMENT, contact.comment)
    }

    @Test
    fun testEquals() {
        val companyEntity = CompanyEntity()

        val contactEntity1 = ContactEntity()
        contactEntity1.n = 1L
        contactEntity1.name = "NAME"
        contactEntity1.companyEntity = companyEntity

        val contactEntity2 = ContactEntity()
        contactEntity2.n = 1L
        contactEntity2.name = "NAME"
        contactEntity2.companyEntity = companyEntity

        assertEquals(contactEntity1, contactEntity2)
    }
}