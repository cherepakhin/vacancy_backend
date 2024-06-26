package ru.perm.v.vacancy.service.impl

import com.querydsl.core.types.Predicate
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.QCompanyEntity
import ru.perm.v.vacancy.filter.CompanyExample
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.repository.CompanyRepository

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

    override fun findAll(predicate: Predicate): List<CompanyDto> {
        val companies = repository.findAll(predicate)
        return companies.map { CompanyMapper.toDto(it) }.toList()
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

        return CompanyMapper.toDto(company)
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

    override fun getByExample(example: CompanyExample): List<CompanyDto> {
        logger.info(example.toString())
        val foundCompanies = this.getByExampleAndSort(example, Sort.by(Sort.Direction.ASC, "n"))
        return foundCompanies
    }

    override fun getByExampleAndSort(companyExample: CompanyExample, sort: Sort): List<CompanyDto> {
        logger.info(companyExample.toString())
        val qCompany = QCompanyEntity.companyEntity
        var predicate = qCompany.n.goe(-1)
        if (companyExample.n != null) {
            predicate = predicate.and(qCompany.n.eq(companyExample.n))
        }
        if (!companyExample.name.isNullOrEmpty()) {
            predicate = predicate.and(qCompany.name.like("%"+companyExample.name+"%"))
        }

        val foundCompanies = repository.findAll(predicate, sort)
        return foundCompanies.map { CompanyMapper.toDto(it) }.toList()
    }

}