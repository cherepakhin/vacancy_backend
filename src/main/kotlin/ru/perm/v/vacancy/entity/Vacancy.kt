package ru.perm.v.vacancy.entity

import javax.persistence.*

@Entity
@Table(name = "vacancy")
open class Vacancy { // "open" needed for JPA
    @Id
    @Column(name = "n", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var n: Long = -1L
    @Column(name = "name", nullable = false)
    open var name: String = ""
    @Column(name = "comment", nullable = false)
    open var comment: String = ""

    // Empty constructor needed for Hibernate
    constructor()


}