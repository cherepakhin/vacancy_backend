package ru.perm.v.vacancy.filter

/**
 * Фильтр для запросов к репозиторию
 * Значение null означает пропустить при отборе
 */
class VacancyExample {
    var n: Long? = null // query equal
    var name: String? = null // will query: LIKE name
    var companyExample: CompanyExample? = null

    constructor()

    constructor(n: Long?, name: String?, companyExample: CompanyExample?) {
        this.n = n
        this.name = name
        this.companyExample =companyExample
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyExample) return false

        if (n != other.n) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "VacancyExample(n=$n, name=$name, companyExample=$companyExample)"
    }
}