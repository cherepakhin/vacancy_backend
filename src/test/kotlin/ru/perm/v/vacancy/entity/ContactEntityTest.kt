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

        val contact = ContactEntity(NAME, EMAIL, PHONE, COMMENT)

        assertNotNull(contact)
        assertNotNull(contact.n)
        assertEquals(NAME, contact.name)
        assertEquals(EMAIL, contact.email)
        assertEquals(PHONE, contact.phone)
        assertEquals(COMMENT, contact.comment)
    }

    @Test
    fun testEquals() {
        val contactEntity1 = ContactEntity()
        contactEntity1.n = 1L
        contactEntity1.name = "NAME"

        val contactEntity2 = ContactEntity()
        contactEntity2.n = 1L
        contactEntity2.name = "NAME"

        assertEquals(contactEntity1, contactEntity2)
    }

    @Test
    fun testToString() {
        val contactEntity = ContactEntity()
        contactEntity.n = 1L
        contactEntity.name = "NAME"
        contactEntity.email = "EMAIL"
        contactEntity.phone = "PHONE"
        contactEntity.comment = "COMMENT"

        assertEquals(
            "ContactEntity(n=1, name='NAME', email='EMAIL', phone='PHONE', comment='COMMENT')",
            contactEntity.toString())
    }
}