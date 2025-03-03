package ru.perm.v.vacancy.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// Generated GigaCode
class ContactExampleTest {
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
}