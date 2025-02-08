package ru.perm.v.vacancy.service

import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.filter.ContactExample

interface ContactService {
    fun getAll(): List<ContactDto>
    fun getAllSortedByField(field: String): List<ContactDto>
    fun findAll(predicate: Predicate): List<ContactDto>
    fun createContact(companyDtoForCreate: ContactDto): ContactDto
    fun getByN(n: Long): ContactDto
    fun updateContact(dto: ContactDto): ContactDto
    fun deleteContact(n: Long): String
    fun getByExample(example: ContactExample): List<ContactDto>
    fun getByExampleAndSort(example: ContactExample, sort: Sort): List<ContactDto>
}