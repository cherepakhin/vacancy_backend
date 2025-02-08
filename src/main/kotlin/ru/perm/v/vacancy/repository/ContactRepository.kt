package ru.perm.v.vacancy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import ru.perm.v.vacancy.entity.ContactEntity

interface ContactRepository: JpaRepository<ContactEntity, Long>,
    JpaSpecificationExecutor<ContactEntity>, QuerydslPredicateExecutor<ContactEntity> {
    @Query(value = "SELECT max(n)+1 FROM ContactEntity")
    fun getNextN(): Long

    @Modifying
    @Query(value = "insert into contact (n,name) values (:n,:name)", nativeQuery = true)
    fun createNew(n: Long, name: String)
}