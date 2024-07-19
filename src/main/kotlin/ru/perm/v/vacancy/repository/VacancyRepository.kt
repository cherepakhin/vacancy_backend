package ru.perm.v.vacancy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.vacancy.entity.VacancyEntity

@Repository
@Transactional
interface VacancyRepository: JpaRepository<VacancyEntity, Long>,
    JpaSpecificationExecutor<VacancyEntity>, QuerydslPredicateExecutor<VacancyEntity> {
    fun findAllByOrderByNAsc(): List<VacancyEntity>
    @Query(value = "SELECT max(n)+1 FROM VacancyEntity")
    fun getNextN(): Long

    @Modifying
    @Query(value = "delete from vacancy", nativeQuery = true)
    fun clear(): Unit

}
