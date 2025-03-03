package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.entity.ContactEntity
import ru.perm.v.vacancy.repository.ContactRepository

class ContactServiceImplTest {
    @Test
    fun createContact() {
        val N = 1L
        val NAME = "NAME"
        val EMAIL = "EMAIL"
        val PHONE = "PHONE"
        val COMMENT = "COMMENT"
        val contactDtoForCreate = ContactEntity(N, NAME, EMAIL, PHONE, COMMENT)
        val repository = mock(ContactRepository::class.java)
        `when`(repository.getNextN()).thenReturn(N)
        `when`(repository.save(contactDtoForCreate)).thenReturn(contactDtoForCreate)
        val service = ContactServiceImpl(repository)

        val savedContact = service.createContact(ContactDto(N, NAME, EMAIL, PHONE, COMMENT))

        assertEquals(N, savedContact.n)
        assertEquals(NAME, savedContact.name)

        verify(repository, times(1)).save(contactDtoForCreate)
    }
}