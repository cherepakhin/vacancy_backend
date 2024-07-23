package ru.perm.v.vacancy.filter

/**
 * Фильтр для запросов к репозиторию
 * Значение null означает пропустить при отборе
 */
class VacancyExample {
    var nn = listOf<Long>()
    var name: String? = null // will query: LIKE name
    var companyExample: CompanyExample? = null

    constructor()

    constructor(nn: List<Long>, name: String?, companyExample: CompanyExample?) {
        this.nn = nn
        this.name = name
        this.companyExample =companyExample
    }

    constructor(nn: List<Long>) {
        this.nn = nn
    }

    constructor(name: String) {
        this.name = name
    }

    constructor(companyExample: CompanyExample) {
        this.companyExample = companyExample
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyExample) return false

        if (nn != other.nn) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nn.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "VacancyExample(n=$nn, name=$name, companyExample=$companyExample)"
    }
}