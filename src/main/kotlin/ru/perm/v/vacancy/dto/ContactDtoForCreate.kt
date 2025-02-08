package ru.perm.v.vacancy.dto

import javax.validation.constraints.Size

class ContactDtoForCreate {
    @field:Size(min=5, max=50)
    var name: String = ""

    var email: String = ""
    var phone: String = ""
    var comment: String = ""

    constructor() {
    }

    constructor(name: String, email: String, phone: String, comment: String): this() {
        this.name = name
        this.email = email
        this.phone = phone
        this.comment = comment
    }

    fun equals(other: ContactDto): Boolean {
        return this.name.equals(other.name)
        && this.email.equals(other.email)
        && this.phone.equals(other.phone)
        && this.comment.equals(other.comment)
    }

    override fun hashCode(): Int {
        return this.name.hashCode() + this.email.hashCode() + this.phone.hashCode() + this.comment.hashCode()
    }

    override fun toString(): String {
        return "ContactDtoForCreate(name=$name, email=$email, phone=$phone, comment=$comment)"
    }
}