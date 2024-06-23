package ru.perm.v.vacancy.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.entity.VacancyEntity
import ru.perm.v.vacancy.mapper.CompanyMapper
import ru.perm.v.vacancy.mapper.VacancyMapper
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.VacancyService
import kotlin.reflect.full.declaredMemberProperties

@Service
class VacancyServiceImpl(
    @Autowired private val repository: VacancyRepository,
    @Autowired private val companyService: CompanyService,
) : VacancyService {

    override fun getByN(n: Long): VacancyDto {
        if (repository.findById(n).isPresent) {
            val vacancyEntity = repository.findById(n).get()
            return VacancyMapper.toDto(vacancyEntity)
        } else {
            throw Exception(String.format(ErrMessage.VACANCY_NOT_FOUND, n));
        }
    }

    override fun getAll(): List<VacancyDto> {
        return this.getAllSortedByField("n")
    }

    override fun getAllSortedByField(sortColumn: String ): List<VacancyDto> {
        if(!existColumn(sortColumn)) {
            throw Exception(String.format(ErrMessage.SORT_COLUMN_NOT_FOUND, sortColumn))
        }
        return repository.findAll(Sort.by(sortColumn)).map { VacancyMapper.toDto(it) }.toList()
    }

    fun existColumn(name: String): Boolean  {
        val fields = VacancyEntity::class.declaredMemberProperties
        return fields.filter { it.name == name}.isNotEmpty()
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