package ru.perm.v.vacancy.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "contact")
class ContactEntity {  // "open" needed for JPA?
    @Id
    @NotNull
    @Column(name = "n", nullable = false)
    var n: Long = -1L
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String = ""
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

    constructor(n: Long, name: String, email: String, phone: String, comment: String) {
        this.n = n
        this.name = name
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactEntity) return false

        if (!n.equals(other.n)) return false
        if (!name.equals(other.name)) return false
        if (!email.equals(other.email)) return false
        if (!phone.equals(other.phone)) return false
        if (!comment.equals(other.comment)) return false

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

    override fun toString(): String {
        return "ContactEntity(n=$n, name='$name', email='$email', phone='$phone', comment='$comment')"
    }
}