package ru.perm.v.vacancy.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.mapper.VacancyMapper
import ru.perm.v.vacancy.repository.VacancyRepository

@Service
class VacancyServiceImpl(@Autowired private val repository: VacancyRepository) : VacancyService {
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

    override fun create(name: String): VacancyDto {
        TODO("Not yet implemented")
    }

    override fun update(n: Long, name: String): VacancyDto {
        TODO("Not yet implemented")
    }

    override fun delete(n: Long): String {
        TODO("Not yet implemented")
    }
}