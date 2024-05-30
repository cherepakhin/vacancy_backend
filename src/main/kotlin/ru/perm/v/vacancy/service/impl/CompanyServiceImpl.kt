package ru.perm.v.vacancy.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.repository.CompanyRepository

@Service
class CompanyServiceImpl(@Autowired private val repository: CompanyRepository) : CompanyService {

    override fun createCompany(name: String): CompanyDto {
        val n = getNextN()
        val company = CompanyEntity(n, name = name)
        val savedCompany = repository.save(company)
        if (savedCompany != null) {
            return CompanyMapper.toDto(savedCompany)
        }
        throw Exception("Company with N=$n not created")
    }

    private fun getNextN(): Long {
        return repository.getNextN()
    }

    override fun getCompanyByN(n: Long): CompanyDto {
        if (repository.findById(n).isPresent) {
            val company = repository.findById(n).get()
            return CompanyMapper.toDto(company)
        } else {
            throw Exception("Company with N=$n not found")
        }
    }

    override fun getCompanies(): List<CompanyDto> {
        return repository.findAll().sortedBy { it.n }.map { CompanyMapper.toDto(it) }
    }

    override fun updateCompany(n: Long, name: String): CompanyDto {
        if (repository.findById(n).isPresent) {
            val company = repository!!.findById(n).get()
            company.name = name
            val savedCompany = repository!!.save(company)
            return CompanyMapper.toDto(savedCompany)
        } else {
            throw Exception("Company with N=$n not found")
        }
    }

    override fun deleteCompany(n: Long): String {
        if (repository.findById(n).isPresent) {
            repository.deleteById(n)
            return "Company with N=$n deleted"
        } else {
            throw Exception("Company with N=$n not found")
        }
    }
}