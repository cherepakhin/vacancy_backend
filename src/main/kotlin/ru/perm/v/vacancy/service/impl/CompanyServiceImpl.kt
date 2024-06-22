package ru.perm.v.vacancy.service.impl

import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.repository.CompanyRepository

@Service
class CompanyServiceImpl(val repository: CompanyRepository, @Lazy val vacancyService: VacancyServiceImpl) : CompanyService {

    override fun getAll(): List<CompanyDto> {
        return repository.findAll().sortedBy { it.n }.map { CompanyMapper.toDto(it) }
    }

    override fun getAllSortedByField(field: String): List<CompanyDto> {
        val sorted = Sort.by(Sort.Direction.ASC, field);

        return repository.findAll(sorted).map { CompanyMapper.toDto(it) }
    }

    override fun createCompany(companyDto: CompanyDto): CompanyDto {
        val n = getNextN()
        val company = CompanyEntity(n, name = companyDto.name)
        val savedCompany = repository.save(company)
        if (savedCompany != null) {
            return CompanyMapper.toDto(savedCompany)
        }
        throw Exception(String.format(ErrMessage.COMPANY_NOT_CREATED, n))
    }

    private fun getNextN(): Long {
        return repository.getNextN()
    }

    @Throws(Exception::class)
    override fun getCompanyByN(n: Long): CompanyDto {
        if (repository.findById(n).isPresent) {
            val company = repository.findById(n).get()
            return CompanyMapper.toDto(company)
        } else {
            throw Exception(String.format(ErrMessage.COMPANY_NOT_FOUND, n))
        }
    }

    override fun updateCompany(n: Long, name: String): CompanyDto {
        if (repository.findById(n).isPresent) {
            val company = repository.findById(n).get()
            company.name = name
            val savedCompany = repository.save(company)
            return CompanyMapper.toDto(savedCompany)
        } else {
            throw Exception(String.format(ErrMessage.COMPANY_NOT_FOUND, n))
        }
    }

    override fun deleteCompany(n: Long): String {

        if (repository.findById(n).isPresent) {
            repository.deleteById(n)
            return String.format(ErrMessage.COMPANY_N_DELETED, n)
        } else {
            throw Exception(String.format(ErrMessage.COMPANY_NOT_FOUND, n))
        }
    }
}