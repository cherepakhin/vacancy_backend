package ru.perm.v.vacancy.mapper

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity

class CompanyMapperTest {

    @Test
    fun toDto() {
        val dto = CompanyMapper.toDto(CompanyEntity(1L, "name"))

        assertEquals(1L, dto.n)
        assertEquals("name", dto.name)
    }

    @Test
    fun toEntity() {
        val entity = CompanyMapper.toEntity(CompanyDto(1L, "name"))

        assertEquals(1L, entity.n)
        assertEquals("name", entity.name)
    }
}