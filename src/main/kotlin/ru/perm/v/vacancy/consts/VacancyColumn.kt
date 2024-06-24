package ru.perm.v.vacancy.consts

/**
 *
 * Need for sorting in REST API
 * 1) Need for valid in REST controller
 * 2) Need for accordance with service
 *
 * may by simple map ({"N": "n"}, {"NAME": "name"})?
 *  NO, VacancyColumn.NAME is better! -> fun getAllSortedByField(sortColumn: VacancyColumn): List<VacancyDto>
 */
enum class VacancyColumn(val value: String) {
    N("n"),
    NAME("name"),
    COMPANY_N("company_n")
}