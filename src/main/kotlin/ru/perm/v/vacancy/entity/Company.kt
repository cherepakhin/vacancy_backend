package ru.perm.v.vacancy.entity

import javax.persistence.*

@Entity
@Table(name = "company")
open class Company { // "open" needed for JPA
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L

    @Column(name = "name", nullable = false)
    open var name: String = ""

    // Empty constructor needed for Hibernate
    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false

        if (n != other.n) return false
        if (name != other.name) return false

        return true
    }
}