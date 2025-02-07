package ru.perm.v.vacancy.mapper

import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.entity.ContactEntity

object ContactMapper {
    fun toDto(contactEntity: ContactEntity): ContactDto {
        return ContactDto(
            contactEntity.n,
            contactEntity.name,
            contactEntity.email,
            contactEntity.phone,
            contactEntity.comment
        )
    }

    fun toEntity(contactDto: ContactDto): ContactEntity {
        return ContactEntity(contactDto.n, contactDto.name, contactDto.email, contactDto.phone, contactDto.comment)
    }
}