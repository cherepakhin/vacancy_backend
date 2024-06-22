package ru.perm.v.vacancy.service.impl

import ru.perm.v.vacancy.dto.CompanyDto

interface CompanyService {
    fun createCompany(companyDto: CompanyDto): CompanyDto
    fun updateCompany(n: Long, name: String): CompanyDto
    fun deleteCompany(n: Long): String // return success ("") or message error
    @Throws(Exception::class)
    fun getCompanyByN(n: Long):  CompanyDto
    fun getAll(): List<CompanyDto>
    fun getAllSortedByField(field: String): List<CompanyDto>
}