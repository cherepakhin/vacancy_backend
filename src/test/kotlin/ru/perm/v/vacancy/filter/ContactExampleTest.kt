package ru.perm.v.vacancy.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

// Generated GigaCode
class ContactExampleTest {

    @Test
    fun testEmptyConstructor() {
        val contactExample = ContactExample()
        assertNull(contactExample.n)
        assertNull(contactExample.name)
    }

    @Test
    fun testGettersAndSetters() {
        val n = 1L
        val name = "Test Contact"

        val contactExample = ContactExample(n, name)

        assertEquals(n, contactExample.n)
        assertEquals(name, contactExample.name)
    }

    @Test
    fun testBoundaryCases() {
        val contactExample = ContactExample(Long.MIN_VALUE, "")
        assertEquals(Long.MIN_VALUE, contactExample.n)
        assertEquals("", contactExample.name)

        val contactExample2 = ContactExample(Long.MAX_VALUE, "a".repeat(1000))
        assertEquals(Long.MAX_VALUE, contactExample2.n)
        assertEquals("a".repeat(1000), contactExample2.name)
    }

    @Test
    fun testEquality() {
        val contactExample1 = ContactExample(1L, "Test Contact")
        val contactExample2 = ContactExample(1L, "Test Contact")
        val contactExample3 = ContactExample(2L, "Test Contact")

        assertEquals(contactExample1, contactExample2)
        assertNotEquals(contactExample1, contactExample3)
    }

    @Test
    fun testHashCode() {
        val contactExample1 = ContactExample(1L, "Test Contact")
        val contactExample2 = ContactExample(1L, "Test Contact")
        val contactExample3 = ContactExample(2L, "Test Contact")

        assertEquals(contactExample1.hashCode(), contactExample2.hashCode())
        assertNotEquals(contactExample1.hashCode(), contactExample3.hashCode())
    }

    @Test
    fun testToString() {
        val contactExample = ContactExample(1L, "Test Contact")
        assertEquals("ContactExample(n=1, name=Test Contact)", contactExample.toString())
    }
}
