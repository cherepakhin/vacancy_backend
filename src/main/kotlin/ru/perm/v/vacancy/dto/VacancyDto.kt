package ru.perm.v.vacancy.dto

open class VacancyDto {
    open var n: Long = -1L
    open var name: String = "-"
    open var comment: String = "-"
    open var company: CompanyDto = CompanyDto(-1L, "-")

    constructor()

    constructor(n: Long, name: String, comment: String, company: CompanyDto)  {
        this.n = n
        this.name = name
        this.comment = comment
        this.company = company
    }
}