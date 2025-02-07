package ru.perm.v.vacancy.dto

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ContactDtoTest {
    @Test
    fun defaultConstructor() {
        val dto = ContactDto()

        assertEquals(-1L, dto.n)
        assertEquals("", dto.name)
        assertEquals("", dto.email)
        assertEquals("", dto.phone)
        assertEquals("", dto.comment)
    }

}