package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.doNothing
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

        val companyDto = service.getCompanyByN(N);

        assertEquals(N, companyDto.n);
        assertEquals(NAME_COMPANY, companyDto.name);
    }

    @Test
    fun `getN should throw exception when company not found`() {
        val repository = mock(CompanyRepository::class.java);
        `when`(repository.findById(999L)).thenReturn(Optional.empty())

        val service = CompanyServiceImpl(repository);

        assertThrows<Exception> { service.getCompanyByN(999L) }
    }

    @Test
    fun getCompanies() {
        val repository = mock(CompanyRepository::class.java);
        val company1 = CompanyEntity(1L, "company1");
        val company2 = CompanyEntity(2L, "company2");
        `when`(repository.findAll()).thenReturn(listOf(company1, company2));

        val service = CompanyServiceImpl(repository);

        assertEquals(2, service.getCompanies().size);
    }

    @Test
    fun updateCompany() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);

        val repository = mock(CompanyRepository::class.java);
        val service = CompanyServiceImpl(repository);
        `when`(repository.findById(N)).thenReturn(Optional.of(companyEntity));
        `when`(repository.save(companyEntity)).thenReturn(companyEntity);

        val changedCompanyDto = service.updateCompany(N, NAME_COMPANY);

        assertEquals(N, changedCompanyDto.n);
        assertEquals(NAME_COMPANY, changedCompanyDto.name);
    }

    @Test
    fun updateNotExistCompany()  {
        val N  =  100L;
        val NAME_COMPANY = "company";
        val repository = mock(CompanyRepository::class.java);
        val service = CompanyServiceImpl(repository);
        `when`(repository.findById(N)).thenReturn(Optional.empty());

        val thrown = assertThrows<Exception>  {
            service.updateCompany(N, NAME_COMPANY);
        }

        assertEquals("Company with N=100 not found", thrown.message);
    }

    @Test
    fun deleteNotExistCompany()  {
        val N  =  100L;
        val repository = mock(CompanyRepository::class.java);
        val service = CompanyServiceImpl(repository);
        `when`(repository.findById(N)).thenReturn(Optional.empty());

        val thrown = assertThrows<Exception>  {
            service.deleteCompany(N);
        }

        assertEquals("Company with N=100 not found", thrown.message);
    }

    @Test
    fun deleteExistCompany()  {
        val N  =  100L;
        val companyEntity  = CompanyEntity(N, "company");
        val repository = mock(CompanyRepository::class.java);
        val service = CompanyServiceImpl(repository);
        `when`(repository.findById(N)).thenReturn(Optional.of(companyEntity));
        doNothing().`when`(repository).deleteById(N);

        val message= service.deleteCompany(N);

        verify(repository, times(1)).deleteById(N);
        assertEquals("Company with N=100 deleted", message);
    }

    @Test
    fun notCreated() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);
        val repository = mock(CompanyRepository::class.java);
        `when`(repository.getNextN()).thenReturn(N);
        `when`(repository.save(companyEntity)).thenReturn(null);

        val service = CompanyServiceImpl(repository);

        val message = assertThrows<Exception> {
            service.createCompany(NAME_COMPANY)
        }.message;
        assertEquals("Company with N=100 not created", message);
    }
}