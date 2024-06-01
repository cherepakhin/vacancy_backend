package ru.perm.v.vacancy.service

import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.entity.VacancyEntity

interface VacancyService {
    @Throws(Exception::class)
    fun getByN(n: Long):  VacancyDto
    fun getAll(): List<VacancyDto>
    fun create(vacancy: VacancyDto): VacancyDto
    fun update(n: Long, name: String): VacancyDto
    fun delete(n: Long): String // return "" if success or return message error
}