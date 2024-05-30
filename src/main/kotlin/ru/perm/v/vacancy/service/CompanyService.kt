package ru.perm.v.vacancy.service.impl

import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity

interface CompanyService {
    @Throws(Exception::class)
    fun getCompany(id: Int):  CompanyDto
    fun getCompanies(): List<CompanyDto>
    fun createCompany(name: String): CompanyDto
    fun updateCompany(id: Int, name: String): CompanyDto
    fun deleteCompany(id: Int): String // return success ("") or message error
}