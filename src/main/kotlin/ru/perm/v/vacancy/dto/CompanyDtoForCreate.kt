package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

/**
 * DTO Class for create Company
 */
class CompanyDtoForCreate {
    @field:Size(min = 5, max = 50)
    val name: String

    constructor(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "CompanyDtoForCreate(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompanyDtoForCreate) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}