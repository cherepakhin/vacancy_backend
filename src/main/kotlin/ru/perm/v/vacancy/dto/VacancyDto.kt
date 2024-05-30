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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyDto) return false

        if (n != other.n) return false
        if (name != other.name) return false
        if (comment != other.comment) return false
        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + company.hashCode()
        return result
    }


}