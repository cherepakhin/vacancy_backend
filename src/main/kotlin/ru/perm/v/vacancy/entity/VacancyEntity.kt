package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "vacancy")
open class VacancyEntity { // "open" needed for JPA
    @NotNull
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L
    @ManyToOne
    @JoinColumn(name = "company_n", nullable = false)
    open var companyEntity: CompanyEntity? = null
    @NotNull
    @Column(name = "name", nullable = false)
    open var name: String = ""
    @NotNull
    @Column(name = "comment", nullable = false)
    open var comment: String = ""
    // add LIST contacts
    // Empty constructor needed for Hibernate
    constructor()
    constructor(n: Long, name: String, comment: String , companyEntity: CompanyEntity) {
        this.n = n
        this.companyEntity = companyEntity
        this.name = name
        this.comment = comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyEntity) return false

        if (n != other.n) return false
        if (companyEntity != other.companyEntity) return false
        if (name != other.name) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + (companyEntity?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }


}