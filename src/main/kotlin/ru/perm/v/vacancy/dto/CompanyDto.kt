package ru.perm.v.vacancy.dto

class CompanyDto(val n: Long, val name: String) {
    override fun toString(): String {
        return "CompanyDto(n=$n, name='$name')"
    }
}