package ru.perm.v.vacancy.service.impl

import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.VacancyEntity

interface VacancyService {
    @Throws(Exception::class)
    fun getByN(n: Long):  VacancyDto
    fun getAll(): List<VacancyDto>
    fun create(name: String): VacancyDto
    fun update(n: Long, name: String): VacancyDto
    fun delete(n: Long): String // return success ("") or message error
}