package ru.perm.v.vacancy.dto

class CompanyDto(val n: Long, val name: String) {
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