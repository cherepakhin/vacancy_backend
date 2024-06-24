package ru.perm.v.vacancy.service.impl

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.repository.CompanyRepository
import java.lang.String.format

@Service
class CompanyServiceImpl(val repository: CompanyRepository, @Lazy val vacancyService: VacancyServiceImpl) :
    CompanyService {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun getAll(): List<CompanyDto> {
        return repository.findAll().sortedBy { it.n }.map { CompanyMapper.toDto(it) }
    }

    override fun getAllSortedByField(field: String): List<CompanyDto> {
        val sorted = Sort.by(Sort.Direction.ASC, field);

        return repository.findAll(sorted).map { CompanyMapper.toDto(it) }
    }

    override fun createCompany(companyDto: CompanyDto): CompanyDto {
        var n = getNextN()
        if (n == null) {
            n = 1L
        }
        logger.info(String.format("getNextN(): %s", n))
        val company = CompanyEntity(n = n, name = companyDto.name)
        logger.info(company.toString())
        repository.createNew(company.n, companyDto.name)
        val createdCompany = getCompanyByN(n)
        logger.info(format("will returned: %s", createdCompany.toString()))
        return createdCompany
    }

    public fun getNextN(): Long {
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