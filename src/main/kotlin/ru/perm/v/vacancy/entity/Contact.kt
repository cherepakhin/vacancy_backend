package ru.perm.v.vacancy.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "contact")
class Contact {
    @Id
    @Column(name = "n", nullable = false)
    open var n: Long = -1L
    @Column(name = "name", nullable = false)
    open var name: String = ""
    @Column(name = "email", nullable = false)
    open var email: String = ""
    @Column(name = "phone", nullable = false)
    open var phone: String = ""
    @Column(name = "comment", nullable = false)
    open var comment: String = ""

    constructor() {
    }

    constructor(n: Long, name: String, email: String, phone: String, comment: String) {
        this.n = n
        this.name = name
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false

        if (n != other.n) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }
}