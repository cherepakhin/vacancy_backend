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

    // Empty constructor needed for Hibernate
    constructor()


}