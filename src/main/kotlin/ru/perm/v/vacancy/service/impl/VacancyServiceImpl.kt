package ru.perm.v.vacancy.service.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.mapper.VacancyMapper
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.VacancyService
import java.lang.String.format

@Service
class VacancyServiceImpl(
    @Autowired private val repository: VacancyRepository,
    @Autowired private val companyService: CompanyService,
) : VacancyService {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun getByN(n: Long): VacancyDto {
        if (repository.findById(n).isPresent) {
            val vacancyEntity = repository.findById(n).get()
            return VacancyMapper.toDto(vacancyEntity)
        } else {
            throw Exception(String.format(ErrMessage.VACANCY_NOT_FOUND, n));
        }
    }

    override fun getAll(): List<VacancyDto> {
        return this.getAllSortedByField(VacancyColumn.N)
    }

    override fun getAllSortedByField(sortColumn: VacancyColumn): List<VacancyDto> {
        logger.info("sortColumn: $sortColumn")
        logger.info(format("sortColumn: %s", sortColumn))
        logger.info(format("sortColumn.value: %s", sortColumn.value))

//        return repository.findAll(Sort.by(sortColumn.value)).map { VacancyMapper.toDto(it) }.toList()
        return repository.findAll(Sort.by(sortColumn.value)).map { VacancyMapper.toDto(it) }.toList()
    }

    override fun create(vacancyDtoForCreate: VacancyDtoForCreate): VacancyDto {
        val companyDto: CompanyDto
        try {
            companyDto = companyService.getCompanyByN(vacancyDtoForCreate.company_n)
        } catch (e: Exception) {
            throw Exception(String.format(ErrMessage.COMPANY_NOT_FOUND, vacancyDtoForCreate.company_n))
        }
        val companyEntity= CompanyMapper.toEntity(companyDto)
        println(companyEntity)
        val n = repository.getNextN()
        println(n)
        val vacancyEntity  = VacancyEntity(n, vacancyDtoForCreate.name, vacancyDtoForCreate.comment, companyEntity)
        val createdVacancy = repository.save(vacancyEntity)
        return VacancyMapper.toDto(createdVacancy)
    }

    override fun update(n: Long, changedVacancyDto: VacancyDto): VacancyDto  {
        getByN(n) // throw if not exists
        companyService.getCompanyByN(changedVacancyDto.company.n) // company exist? throw if not exists
        val savedVacancyEntity = repository.save(VacancyMapper.toEntity(changedVacancyDto))
        return VacancyMapper.toDto(savedVacancyEntity)
    }

    @Throws(Exception::class)
    override fun delete(n: Long): String {
        try {
            getByN(n) // throw if not exists
            repository.deleteById(n)
        } catch  (e: Exception)  {
            throw Exception(e.message)
        }
        return "OK"
    }
}