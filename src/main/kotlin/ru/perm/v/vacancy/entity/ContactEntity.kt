package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "contact")
class ContactEntity {  // "open" needed for JPA?
    @Id
    @NotNull
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var n: Long = -1L
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String = ""
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_n", nullable = false)
    lateinit var companyEntity: CompanyEntity
    @NotNull
    @Column(name = "email", nullable = false)
    var email: String = ""
    @NotNull
    @Column(name = "phone", nullable = false)
    var phone: String = ""
    @NotNull
    @Column(name = "comment", nullable = false)
    var comment: String = ""

    constructor() // need for JPA

    constructor(name: String, email: String, phone: String, comment: String, companyEntity: CompanyEntity) {
        this.name = name
        this.companyEntity = companyEntity
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactEntity) return false

        if (n != other.n) return false
        if (name != other.name) return false
        if (companyEntity != other.companyEntity) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + companyEntity.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }


}