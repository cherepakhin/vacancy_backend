package ru.perm.v.vacancy.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.mapper.VacancyMapper
import ru.perm.v.vacancy.repository.VacancyRepository
import ru.perm.v.vacancy.service.VacancyService

@Service
class VacancyServiceImpl(
    @Autowired private val repository: VacancyRepository,
    @Autowired private val companyService: CompanyService
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
        return repository.findAll().map { VacancyMapper.toDto(it) }.toList()
    }

    override fun create(vacancyDto: VacancyDto): VacancyDto {
        try {
            companyService.getCompanyByN(vacancyDto.company.n)
        } catch (e: Exception) {
            throw Exception(String.format(ErrMessage.COMPANY_NOT_FOUND, vacancyDto.company.n))
        }
        val createdVacancy = repository.save(VacancyMapper.toEntity(vacancyDto))
        return VacancyMapper.toDto(createdVacancy)
    }

    override fun update(n: Long, changedVacancyDto: VacancyDto): VacancyDto  {
        getByN(n) // throw if not exists
        companyService.getCompanyByN(changedVacancyDto.company.n) // company exist? throw if not exists
        val savedVacancyEntity = repository.save(VacancyMapper.toEntity(changedVacancyDto))
        return VacancyMapper.toDto(savedVacancyEntity)
    }

    override fun delete(n: Long): String {
        getByN(n) // throw if not exists
        try {
            repository.deleteById(n)
        } catch  (e: Exception)  {
            throw Exception(e.message)
        }
        return "OK"
    }
}