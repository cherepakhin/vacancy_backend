package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ru.perm.v.vacancy.entity.CompanyEntity
import ru.perm.v.vacancy.repository.CompanyRepository
import java.util.*

class CompanyServiceImplTest {

    @Test
    fun createCompany() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);
        val repository = mock(CompanyRepository::class.java);
        `when`(repository.getNextN()).thenReturn(N);
        `when`(repository.save(companyEntity)).thenReturn(companyEntity);

        val service = CompanyServiceImpl(repository);

        val savedCompany = service.createCompany(NAME_COMPANY);

        assertEquals(N, savedCompany.n);
        assertEquals(NAME_COMPANY, savedCompany.name);
    }

    @Test
    fun getCompanyByN() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);

        val repository = mock(CompanyRepository::class.java);
        val service = CompanyServiceImpl(repository);
        `when`(repository.findById(N)).thenReturn(Optional.of(companyEntity));

        val companyDto  = service.getCompanyByN(N);

        assertEquals(N, companyDto.n);
        assertEquals(NAME_COMPANY, companyDto.name);
    }
}