package ru.perm.v.vacancy.service.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.entity.QVacancyEntity
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.filter.VacancyExample
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

        return repository.findAll(Sort.by(sortColumn.value)).map { VacancyMapper.toDto(it) }.toList()
    }

    @Throws(Exception::class)
    override fun create(vacancyDtoForCreate: VacancyDtoForCreate): VacancyDto {
        logger.info("create vacancy $vacancyDtoForCreate")
        try  {
            val temp= companyService.getCompanyByN(vacancyDtoForCreate.company_n)
            logger.info(format("temp: %s", temp))
        } catch  (e: Exception)  {
            logger.error(e.message)
            throw Exception(e.message)
        }
        val n = repository.getNextN()

        logger.info("Next n vacancy $n")

        val companyEntity = CompanyEntity()
        companyEntity.n = vacancyDtoForCreate.company_n

        val vacancyEntity = VacancyEntity(n, vacancyDtoForCreate.name, vacancyDtoForCreate.comment, companyEntity)
        logger.info(format("vacancyEntity: %s", vacancyEntity))
        repository.save(vacancyEntity)
        val checkVacancy= repository.getById(n)
        logger.info("created vacancy getById $checkVacancy")
        return VacancyMapper.toDto(checkVacancy)
    }

    override fun update(n: Long, changedVacancyDto: VacancyDto): VacancyDto {
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
        } catch (e: Exception) {
            throw Exception(e.message)
        }
        return "OK"
    }

    override fun getByExample(vacancyExample: VacancyExample): List<VacancyDto> {
        logger.info(vacancyExample.toString())
        val qVacancy = QVacancyEntity.vacancyEntity
        var predicate = qVacancy.n.goe(-1) // start query
        if (vacancyExample.nn.isNotEmpty()) {
            predicate = predicate.and(qVacancy.n.`in`(vacancyExample.nn))
        }
        if (!vacancyExample.name.isNullOrEmpty()) {
            predicate = predicate.and(qVacancy.name.like("%" + vacancyExample.name + "%"))
        }

        val sort= Sort.by(Sort.Direction.ASC, "n")
        val foundVacancies = repository.findAll(predicate, sort)
        return foundVacancies.map { VacancyMapper.toDto(it) }.toList()
    }
}