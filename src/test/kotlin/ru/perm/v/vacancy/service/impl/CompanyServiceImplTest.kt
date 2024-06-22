package ru.perm.v.vacancy.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.doNothing
import ru.perm.v.vacancy.dto.CompanyDto
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
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        `when`(repository.getNextN()).thenReturn(N);
        `when`(repository.save(companyEntity)).thenReturn(companyEntity);

        val service = CompanyServiceImpl(repository, vacancyService);

        val savedCompany = service.createCompany(CompanyDto(N, NAME_COMPANY));

        assertEquals(N, savedCompany.n);
        assertEquals(NAME_COMPANY, savedCompany.name);
    }

    @Test
    fun getCompanyByN() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);

        val repository = mock(CompanyRepository::class.java);
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
        `when`(repository.findById(N)).thenReturn(Optional.of(companyEntity));

        val companyDto = service.getCompanyByN(N);

        assertEquals(N, companyDto.n);
        assertEquals(NAME_COMPANY, companyDto.name);
    }

    @Test
    fun `getN should throw exception when company not found`() {
        val repository = mock(CompanyRepository::class.java);
        `when`(repository.findById(999L)).thenReturn(Optional.empty())

        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);

        assertThrows<Exception> { service.getCompanyByN(999L) }
    }

    @Test
    fun getCompanies() {
        val repository = mock(CompanyRepository::class.java);
        val company1 = CompanyEntity(1L, "company1");
        val company2 = CompanyEntity(2L, "company2");
        `when`(repository.findAll()).thenReturn(listOf(company1, company2));

        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository , vacancyService);

        assertEquals(2, service.getAll().size);
    }

    @Test
    fun updateCompany() {
        val N = 100L;
        val NAME_COMPANY = "company";
        val companyEntity = CompanyEntity(N, NAME_COMPANY);

        val repository = mock(CompanyRepository::class.java);
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
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
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
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
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
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
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
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

        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service = CompanyServiceImpl(repository, vacancyService);
        val companyDto  = CompanyDto(N, NAME_COMPANY);
        val message = assertThrows<Exception> {
            service.createCompany(companyDto);
        }.message;
        assertEquals("Company with N=100 not created", message);
    }

    @Test
    fun getAll_CheckSort() {
        val N1 = 1L;
        val NAME_COMPANY1 = "company1";
        val companyEntity1 = CompanyEntity(N1, NAME_COMPANY1);

        val N2  =  2L;
        val NAME_COMPANY2  =  "company2";
        val companyEntity2  = CompanyEntity(N2, NAME_COMPANY2);

        val repository = mock(CompanyRepository::class.java);
        `when`(repository.findAll()).thenReturn(listOf(companyEntity2, companyEntity1));
        val vacancyService  = mock(VacancyServiceImpl::class.java);
        val service  = CompanyServiceImpl(repository, vacancyService);

        val companies  = service.getAll();
        assertEquals(2, companies.size);
        assertEquals(N1, companies[0].n);
        assertEquals(NAME_COMPANY1, companies[0].name);
        assertEquals(N2, companies[1].n);
        assertEquals(NAME_COMPANY2, companies[1].name);
    }
}