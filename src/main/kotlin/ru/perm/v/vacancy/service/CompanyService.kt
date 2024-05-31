package ru.perm.v.vacancy.service.impl

import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity

interface CompanyService {
    @Throws(Exception::class)
    fun getCompanyByN(n: Long):  CompanyDto
    fun getAll(): List<CompanyDto>
    fun createCompany(name: String): CompanyDto
    fun updateCompany(n: Long, name: String): CompanyDto
    fun deleteCompany(n: Long): String // return success ("") or message error
}