package ru.perm.v.vacancy.repository

import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.vacancy.entity.CompanyEntity

@Repository
@Transactional
interface CompanyRepository: JpaRepository<CompanyEntity, Long>,
    JpaSpecificationExecutor<CompanyEntity>, QuerydslPredicateExecutor<CompanyEntity> {
    fun findAllByOrderByNAsc(): List<CompanyEntity>
    @Query(value = "SELECT max(n)+1 FROM CompanyEntity")
    fun getNextN(): Long

    @Modifying
    @Query(value = "insert into company (n,name) values (:n,:name)", nativeQuery = true)
    fun createNew(n: Long, name: String): Unit
}
