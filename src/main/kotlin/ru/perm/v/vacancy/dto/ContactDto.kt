package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

class ContactDto {
    var n: Long = -1L

    @field:Size(min=5, max=50)
    var name: String = ""

    var email: String = ""
    var phone: String = ""
    var comment: String = ""

    constructor() {
    }

    constructor(n: Long, name: String, email: String, phone: String, comment: String): this() {
        this.n = n
        this.name = name
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    fun equals(other: ContactDto): Boolean {
        return this.n == other.n
        && this.name == other.name
        && this.email == other.email
        && this.phone == other.phone
        && this.comment == other.comment
    }

    override fun hashCode(): Int {
        return this.n.hashCode() + this.name.hashCode() + this.email.hashCode() + this.phone.hashCode() + this.comment.hashCode()
    }

    override fun toString(): String {
        return "ContactDto(n=$n, name=$name, email=$email, phone=$phone, comment=$comment)"
    }
}