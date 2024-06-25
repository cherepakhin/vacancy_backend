package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

class CompanyDto {
    val n: Long

    @field:Size(min=5, max=50)
    val name: String

    constructor() {
        this.n = 0
        this.name = ""
    }

    constructor(n: Long, name: String) {
        this.n = n
        this.name = name
    }
    override fun toString(): String {
        return "CompanyDto(n=$n, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompanyDto) return false

        if (n != other.n) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}