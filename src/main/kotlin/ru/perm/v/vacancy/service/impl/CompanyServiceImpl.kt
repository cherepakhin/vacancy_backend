package ru.perm.v.vacancy.service.impl

import org.springframework.stereotype.Service
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.repository.CompanyRepository

@Service
class CompanyServiceImpl : CompanyService {

    val repository: CompanyRepository;
    val companyMapper: CompanyMapper = CompanyMapper();

    constructor(repository: CompanyRepository) {
        this.repository = repository
    }

    override fun createCompany(name: String): CompanyDto {
        val company = CompanyEntity(name = name)
        val savedCompany= repository.save(company)
        return companyMapper.toDto(savedCompany)
    }

    override fun getCompany(id: Int): CompanyDto {
        TODO("Not yet implemented")
    }

    override fun getCompanies(): List<CompanyDto> {
        TODO("Not yet implemented")
    }

    override fun updateCompany(id: Int, name: String): CompanyDto {
        TODO("Not yet implemented")
    }

    override fun deleteCompany(id: Int): String {
        TODO("Not yet implemented")
    }
}