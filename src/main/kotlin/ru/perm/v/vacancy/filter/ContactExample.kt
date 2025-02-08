package ru.perm.v.vacancy.filter

/**
 * Фильтр для запросов к репозиторию
 * Значение null означает пропустить при отборе
 */
class ContactExample {
    var n: Long? = null
    var name: String? = null

    constructor()

    constructor(n: Long, name: String) {
        this.n = n
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactExample) return false

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
        return "ContactExample(n=$n, name=$name)"
    }


}