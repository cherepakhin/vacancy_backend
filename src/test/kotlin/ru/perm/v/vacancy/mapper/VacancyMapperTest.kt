package ru.perm.v.vacancy.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.ContactEntity
import ru.perm.v.vacancy.entity.VacancyEntity

class VacancyMapperTest {

    @Test
    fun toDto() {
        val N = 100L
        val NAME = "name"
        val COMMENT = "comment"

        val COMPANY_ENTITY = CompanyEntity(10L, "COMPANY")

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        val entity = VacancyEntity(N, NAME, COMMENT, COMPANY_ENTITY, contactEntity10)
        val dto = VacancyMapper.toDto(entity)

        val COMPANY_DTO = CompanyDto(10L, "COMPANY")
        val CONTACTDTO10 = ContactDto()
        CONTACTDTO10.n = 10L
        assertEquals(VacancyDto(N, NAME, COMMENT, COMPANY_DTO, CONTACTDTO10), dto)
    }

    @Test
    fun toDtoForEmptyCompany() {
        val N = 100L
        val NAME = "name"
        val COMMENT = "comment"

        val entity = VacancyEntity()
        entity.n = N
        entity.name = NAME
        entity.comment = COMMENT
        entity.contact = ContactEntity()
        entity.contact.n = 10L
        val dto = VacancyMapper.toDto(entity)

        val EMPTY_COMPANY_DTO = CompanyDto(-1, "")
        val CONTACT_DTO_10 = ContactDto()
        CONTACT_DTO_10.n = 10L
        assertEquals(VacancyDto(N, NAME, COMMENT, EMPTY_COMPANY_DTO,CONTACT_DTO_10), dto)
    }

    @Test
    fun toEntity() {
        val N = 100L
        val NAME = "name"
        val COMMENT = "comment"
        val COMPANY_DTO = CompanyDto(10L, "COMPANY")
        val CONTACT_DTO_10 = ContactDto()
        CONTACT_DTO_10.n = 10L
        val vacancyDTO = VacancyDto(N, NAME, COMMENT, COMPANY_DTO, CONTACT_DTO_10)
        val vacancyEntity = VacancyMapper.toEntity(vacancyDTO)

        val contactEntity10 = ContactEntity()
        contactEntity10.n = 10L

        assertEquals(CompanyEntity(10L, "COMPANY"), vacancyEntity.company)
        assertEquals(VacancyEntity(N, NAME, COMMENT, CompanyEntity(10L, "COMPANY"), contactEntity10), vacancyEntity)
    }
}