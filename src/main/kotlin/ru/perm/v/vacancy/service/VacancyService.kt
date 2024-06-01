package ru.perm.v.vacancy.service

import ru.perm.v.vacancy.dto.VacancyDto

interface VacancyService {
    @Throws(Exception::class)
    fun getByN(n: Long): VacancyDto
    fun getAll(): List<VacancyDto>
    fun create(vacancy: VacancyDto): VacancyDto

    /**
     * update by n, changedVacancyDto - container for update
     * @param n - id vacancy
     * @param changedVacancyDto - container for new values
     * @return - updated vacancy
     */
    fun update(n: Long, changedVacancyDto: VacancyDto): VacancyDto
    fun delete(n: Long): String // return "" if success or return message error
}