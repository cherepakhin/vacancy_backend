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
        val N = 100L
        val NAME = "name"
        val EMAIL = "EMAIL"
        val PHONE = "PHONE"
        val COMMENT = "COMMENT"

        val contact = Contact(N, NAME, EMAIL, PHONE, COMMENT)

        assertNotNull(contact)
        assertEquals(N, contact.n)
        assertEquals(NAME, contact.name)
        assertEquals(EMAIL, contact.email)
        assertEquals(PHONE, contact.phone)
        assertEquals(COMMENT, contact.comment)
    }
}