package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "vacancy")
class VacancyEntity {
    @NotNull
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var n: Long = -1L
    @ManyToOne
    @JoinColumn(name = "company_n", nullable = false)
    var companyEntity: CompanyEntity? = null
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String = ""
    @NotNull
    @Column(name = "comment", nullable = false)
    var comment: String = ""
    // add LIST contacts

    constructor() // Empty constructor needed for Hibernate
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