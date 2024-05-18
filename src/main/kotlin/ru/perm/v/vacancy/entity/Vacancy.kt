package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "vacancy")
open class Vacancy { // "open" needed for JPA
    @NotNull
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L
    @ManyToOne
    @JoinColumn(name = "company_n", nullable = false)
    open var company: Company? = null
    @NotNull
    @Column(name = "description", nullable = false)
    open var description: String = ""
    @NotNull
    @Column(name = "comment", nullable = false)
    open var comment: String = ""

    // Empty constructor needed for Hibernate
    constructor()


}