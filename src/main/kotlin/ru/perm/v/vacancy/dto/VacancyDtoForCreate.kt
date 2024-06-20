package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

class VacancyDtoForCreate(
    @field:Size(min=5, max=50)
    var name: String = "-",
    var comment: String = "-",
    var company_n: Long  =  0
) {
    override fun toString(): String {
        return "VacancyDto(name='$name', comment='$comment', company_n=$company_n)"
    }
}