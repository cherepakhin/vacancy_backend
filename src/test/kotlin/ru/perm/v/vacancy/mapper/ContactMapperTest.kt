package ru.perm.v.vacancy.mapper

import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.entity.ContactEntity
import kotlin.test.assertEquals

class ContactMapperTest {
    @Test
    fun toDTO() {
        val emtity = ContactEntity(1L, "name", "email", "phone", "comment")
        val dto = ContactMapper.toDto(emtity)

        assertEquals(dto.n, emtity.n)
        assertEquals(dto.name, emtity.name)
        assertEquals(dto.email, emtity.email)
        assertEquals(dto.phone, emtity.phone)
        assertEquals(dto.comment, emtity.comment)
    }

    @Test
    fun toEntity() {
        val dto = ContactDto(1L, "name", "email", "phone", "comment")

        val entity = ContactMapper.toEntity(dto)

        assertEquals(entity.n, dto.n)
        assertEquals(entity.name, dto.name)
        assertEquals(entity.email, dto.email)
        assertEquals(entity.phone, dto.phone)
        assertEquals(entity.comment, dto.comment)
    }
}