package ru.perm.v.vacancy.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ContactTest {
    @Test
    fun defaultConstructor() {
        val c = Contact()

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

        val contact = Contact(NAME, EMAIL, PHONE, COMMENT)

        assertNotNull(contact)
        assertNotNull(contact.n)
        assertEquals(NAME, contact.name)
        assertEquals(EMAIL, contact.email)
        assertEquals(PHONE, contact.phone)
        assertEquals(COMMENT, contact.comment)
    }
}