package ru.perm.v.vacancy.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "company")
open class Company { // "open" needed for JPA
    @Id
    @Column(name = "n", nullable = false)
    open var n: Long = -1L
    @Column(name = "name", nullable = false)
    open var name: String = ""

    // Empty constructor needed for Hibernate
    constructor()

    constructor(n: Long, name: String) {
        this.n = n
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false

        if (n != other.n) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Company(n=$n, name='$name')"
    }


}