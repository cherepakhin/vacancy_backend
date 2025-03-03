package ru.perm.v.vacancy.service.impl

import com.querydsl.core.types.Predicate
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.perm.v.vacancy.consts.ErrMessage
import ru.perm.v.vacancy.dto.ContactDto
import ru.perm.v.vacancy.entity.ContactEntity
import ru.perm.v.vacancy.filter.ContactExample
import ru.perm.v.vacancy.mapper.ContactMapper
import ru.perm.v.vacancy.repository.ContactRepository
import ru.perm.v.vacancy.service.ContactService

@Service
class ContactServiceImpl(val repository: ContactRepository) : ContactService {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun createContact(contactDtoForCreate: ContactDto): ContactDto {
        val n = getNextN()
        logger.info("getNextN(): $n")
        val contact = ContactEntity(
            n = contactDtoForCreate.n,
            name = contactDtoForCreate.name,
            email = contactDtoForCreate.email,
            phone = contactDtoForCreate.phone,
            comment = contactDtoForCreate.comment
        )
        logger.info(contact.toString())
        repository.save<ContactEntity>(contact)

        return ContactMapper.toDto(contact)
    }

    override fun getAll(): List<ContactDto> {
        logger.info("getAll()")
        return repository.findAll().sortedBy { it.n }.map { ContactMapper.toDto(it) }
    }

    override fun getAllSortedByField(field: String): List<ContactDto> {
        val sorted = Sort.by(Sort.Direction.ASC, field)

        return repository.findAll(sorted).map { ContactMapper.toDto(it) }
    }

    override fun findAll(predicate: Predicate): List<ContactDto> {
        val contacts = repository.findAll(predicate)
        return contacts.map { ContactMapper.toDto(it) }.toList()
    }

    fun getNextN(): Long {
        return repository.getNextN()
    }

    @Throws(Exception::class)
    override fun getByN(n: Long): ContactDto {
        if (repository.findById(n).isPresent) {
            val contact = repository.findById(n).get()
            return ContactMapper.toDto(contact)
        } else {
            throw Exception(String.format(ErrMessage.CONTACT_NOT_FOUND, n))
        }
    }

    override fun updateContact(dto: ContactDto): ContactDto {
        if (repository.findById(dto.n).isPresent) {
            val contact = repository.findById(dto.n).get()
            contact.name = dto.name
            contact.email = dto.email
            contact.phone = dto.phone
            contact.comment = dto.comment

//            company.name = name
            val savedContact = repository.save(contact)
            return ContactMapper.toDto(savedContact)
        } else {
            throw Exception(String.format(ErrMessage.CONTACT_NOT_FOUND, dto.n))
        }
    }

    override fun deleteContact(n: Long): String {
        if (repository.findById(n).isPresent) {
            repository.deleteById(n)
            return String.format(ErrMessage.CONTACT_N_DELETED, n)
        } else {
            throw Exception(String.format(ErrMessage.CONTACT_NOT_FOUND, n))
        }
    }

    override fun getByExample(example: ContactExample): List<ContactDto> {
        logger.info(example.toString())
        val foundCompanies = this.getByExampleAndSort(example, Sort.by(Sort.Direction.ASC, "n"))
        return foundCompanies
    }

    override fun getByExampleAndSort(example: ContactExample, sort: Sort): List<ContactDto> {
        return emptyList()
//TODO: not released override fun getByExampleAndSort
//        logger.info(example.toString())
//        val qCompany = QCompanyEntity.companyEntity
//        val qCompany = QCompanyEntity.companyEntity
//        var predicate = qCompany.n.goe(-1)
//        if (companyExample.n != null) {
//            predicate = predicate.and(qCompany.n.eq(companyExample.n))
//        }
//        if (!companyExample.name.isNullOrEmpty()) {
//            predicate = predicate.and(qCompany.name.like("%" + companyExample.name + "%"))
//        }
//
//        val foundCompanies = repository.findAll(predicate, sort)
//        return foundCompanies.map { ContactMapper.toDto(it) }.toList()
    }
}