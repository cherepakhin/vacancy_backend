package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.*
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

        val contact = ContactEntity(NAME, COMPANY, EMAIL, PHONE, COMMENT)

        assertNotNull(contact)
        assertNotNull(contact.n)
        assertEquals(NAME, contact.name)
        assertEquals(COMPANY, contact.companyEntity)
        assertEquals(EMAIL, contact.email)
        assertEquals(PHONE, contact.phone)
        assertEquals(COMMENT, contact.comment)
    }
}