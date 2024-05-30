package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import ru.perm.v.vacancy.entity.CompanyEntity
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import ru.perm.v.vacancy.repository.CompanyRepository

class CompanyServiceImplTest {

    @Test
    fun createCompany() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);
//        doReturn(copanyEntity).`when`(repository).save(NAME_COMPANY);
        val repository = mock(CompanyRepository::class.java);
        `when`(repository.getNextN()).thenReturn(N);
        `when`(repository.save(companyEntity)).thenReturn(companyEntity);

        val service  =  CompanyServiceImpl(repository);

        val savedCompany = service.createCompany(NAME_COMPANY);

        assertEquals(N, savedCompany.n);
        assertEquals(NAME_COMPANY, savedCompany.name);
    }
}