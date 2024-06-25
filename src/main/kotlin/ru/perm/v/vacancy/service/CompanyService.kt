package ru.perm.v.vacancy.service.impl

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import ru.perm.v.vacancy.dto.CompanyDto

interface CompanyService {
    fun createCompany(companyDto: CompanyDto): CompanyDto
    fun updateCompany(n: Long, name: String): CompanyDto
    fun deleteCompany(n: Long): String // return success ("") or message error
    @Throws(Exception::class)
    fun getCompanyByN(n: Long):  CompanyDto
    fun getAll(): List<CompanyDto>
    fun getAllSortedByField(field: String): List<CompanyDto>
    fun getByExample(example: CompanyDto): List<CompanyDto>
    fun findAll(predicate: Predicate): List<CompanyDto>
}