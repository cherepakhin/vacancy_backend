package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

open class VacancyDto {
    var n: Long = -1L
    @field:Size(min=5, max=50)
    var name: String = "-"
    var comment: String = "-"
    var company: CompanyDto = CompanyDto(-1L, "-")
    var contact: ContactDto = ContactDto()

    constructor()

    constructor(n: Long, name: String, comment: String, company: CompanyDto, contact: ContactDto )  {
        this.n = n
        this.name = name
        this.comment = comment
        this.company = company
        this.contact = contact
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyDto) return false

        if (!n.equals(other.n)) return false
        if (!name.equals(other.name)) return false
        if (!comment.equals(other.comment)) return false
        if (!company.equals(other.company)) return false
        if (!contact.equals(other.contact)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + company.hashCode()
        result = 31 * result + contact.hashCode()
        return result
    }

    override fun toString(): String {
        return "VacancyDto(n=$n, name='$name', comment='$comment', company=$company, contact=$contact)"
    }
}