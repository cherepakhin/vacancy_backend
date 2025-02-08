package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

class ContactDto {
    var n: Long = -1L

    @field:Size(min = 5, max = 50)
    var name: String = ""
    var email: String = ""
    var phone: String = ""
    var comment: String = ""

    constructor() {
    }

    constructor(n: Long, name: String, email: String, phone: String, comment: String) : this() {
        this.n = n
        this.name = name
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactDto) return false

        val result = this.n.equals(other.n)
                && this.name.equals(other.name)
                && this.email.equals(other.email)
                && this.phone.equals(other.phone)
                && this.comment.equals(other.comment)
        return result
    }

    override fun hashCode(): Int {
        return this.n.hashCode() +
                this.name.hashCode() +
                this.email.hashCode() +
                this.phone.hashCode() +
                this.comment.hashCode()
    }

    override fun toString(): String {
        return "ContactDto(n=$n, name=$name, email=$email, phone=$phone, comment=$comment)"
    }
}