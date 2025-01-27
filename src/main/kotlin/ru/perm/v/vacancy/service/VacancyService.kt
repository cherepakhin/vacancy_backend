package ru.perm.v.vacancy.service

import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.filter.VacancyExample

interface VacancyService {
    @Throws(Exception::class)
    fun getByN(n: Long): VacancyDto
    fun getAll(): List<VacancyDto>
    fun getAllSortedByField(sortColumn: VacancyColumn): List<VacancyDto>
    fun create(vacancyDtoForCreate: VacancyDtoForCreate): VacancyDto

    /**
     * update by n, changedVacancyDto - container for update
     * @param n - id vacancy
     * @param changedVacancyDto - container for new values
     * @return - updated vacancy
     */
    fun update(n: Long, changedVacancyDto: VacancyDto): VacancyDto
    fun delete(n: Long): String // return "" if success or return message error

    fun getByExample(example: VacancyExample): List<VacancyDto>
}