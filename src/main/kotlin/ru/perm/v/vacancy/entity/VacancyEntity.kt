package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "vacancy")
class VacancyEntity {
    @NotNull
    @Id
    @Column(name = "n", nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO, generator  = "native")
    var n: Long = -1L

    @ManyToOne
    @JoinColumn(name = "company_n", nullable = false)
    var company: CompanyEntity = CompanyEntity()

    @ManyToOne
    @JoinColumn(name = "contact_n", nullable = false)
    var contact: ContactEntity = ContactEntity()

    @NotNull
    @Column(name = "name", nullable = false)
    var name: String = ""

    @NotNull
    @Column(name = "comment", nullable = false)
    var comment: String = ""
    // add LIST contacts

    constructor() // Empty constructor needed for Hibernate
    constructor(
        n: Long,
        name: String,
        comment: String,
        companyEntity: CompanyEntity,
        contactEntity: ContactEntity
    ) : this(n) {
        this.company = companyEntity
        this.name = name
        this.comment = comment
        this.contact = contactEntity
    }

    constructor(n: Long) : this() {
        this.n = n
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyEntity) return false

        if (n.equals(other.n) &&
            name.equals(other.name) &&
            company.equals(other.company) &&
            contact.equals(other.contact) &&
            comment.equals(other.comment)
        ) return true
        return false
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + company.hashCode()
        result = 31 * result + contact.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }

    override fun toString(): String {
        return "VacancyEntity(n=$n, company=$company, contact=$contact, name='$name', comment='$comment')"
    }
}