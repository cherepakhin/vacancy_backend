package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "contact")
class Contact {
    @Id
    @NotNull
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L
    @NotNull
    @Column(name = "name", nullable = false)
    open var name: String = ""
    @NotNull
    @Column(name = "company", nullable = false)
    @ManyToOne
    open lateinit var company: Company
    @NotNull
    @Column(name = "email", nullable = false)
    open var email: String = ""
    @NotNull
    @Column(name = "phone", nullable = false)
    open var phone: String = ""
    @NotNull
    @Column(name = "comment", nullable = false)
    open var comment: String = ""

    constructor() {
    }

    constructor(name: String, company: Company, email: String, phone: String, comment: String) {
        this.name = name
        this.company = company
        this.email = email
        this.phone = phone
        this.comment = comment
    }

}