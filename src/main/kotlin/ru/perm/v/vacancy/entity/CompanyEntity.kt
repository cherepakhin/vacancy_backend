package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "company")
open class CompanyEntity { // "open" needed for JPA
    @Id
    @NotNull
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L

    @NotNull
    @Column(name = "name", nullable = false)
    open var name: String = ""

    // Empty constructor needed for Hibernate
    constructor()

    constructor(name: String) {
        this.name = name
    }
    constructor(n:Long, name: String) {
        this.n = n
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompanyEntity) return false

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
        return "CompanyEntity(n=$n, name='$name')"
    }
}