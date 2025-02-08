package ru.perm.v.vacancy.dto

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ContactForCreateDtoTest {
    @Test
    fun defaultConstructor() {
        val dto = ContactDtoForCreate()

        assertEquals("", dto.name)
        assertEquals("", dto.email)
        assertEquals("", dto.phone)
        assertEquals("", dto.comment)
    }

}