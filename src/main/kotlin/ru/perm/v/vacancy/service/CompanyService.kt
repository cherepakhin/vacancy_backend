package ru.perm.v.vacancy.service.impl

import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.filter.CompanyExample

interface CompanyService {
    fun createCompany(companyDto: CompanyDto): CompanyDto
    fun updateCompany(n: Long, name: String): CompanyDto
    fun deleteCompany(n: Long): String // return success ("") or message error
    @Throws(Exception::class)
    fun getCompanyByN(n: Long):  CompanyDto
    fun getAll(): List<CompanyDto>
    fun getAllSortedByField(field: String): List<CompanyDto>
    fun getByExample(example: CompanyExample): List<CompanyDto>
    fun findAll(predicate: Predicate): List<CompanyDto>
    fun getByExampleAndSort(companyExample: CompanyExample, sort: Sort): List<CompanyDto>
}