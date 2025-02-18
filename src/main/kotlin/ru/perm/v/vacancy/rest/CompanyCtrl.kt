package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.dto.CompanyDtoForCreate
import ru.perm.v.vacancy.filter.CompanyExample
import ru.perm.v.vacancy.service.CompanyService
import ru.perm.v.vacancy.validators.ValidatorCompanyDto
import ru.perm.v.vacancy.validators.ValidatorCompanyDtoForCreate
import kotlin.collections.isNotEmpty

@RestController
@RequestMapping("/company")
@Api(tags = ["Rest Company"])
class CompanyCtrl(val companyService: CompanyService) {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val validSortColumns = listOf("n", "name")

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    @Cacheable("company_echo")
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String,
    ): String {
        logger.info("echo $mes")
        return mes
    }

    @GetMapping("/{n}")
    @ApiOperation("Get Product by N")
    @Cacheable("companies")
    fun getByN(
        @Parameter(
            description = "N(ID) Product."
        )
        @PathVariable
        n: Long,
    ): CompanyDto {
        return companyService.getCompanyByN(n)
    }

    @GetMapping("/")
    @ApiOperation("Get all companies")
    fun getAll(): List<CompanyDto> {
        logger.info("Get all companies")
        val companies = companyService.getAllSortedByField("n")
        logger.info(companies.toString())
        return companies
    }

    @GetMapping("/sortByColumn/{column}")
    @ApiOperation("Get all companies sort by column")
    // rewrite with criteria search???
    fun getAllSortByColumn(@PathVariable("column") column: String): List<CompanyDto> {
        var sortColumn = "n"
        logger.info("Get all companies sort by column $column")
        if (column.isNotEmpty()) {
            if (validSortColumns.contains(column)) {
                sortColumn = column
            } else {
                logger.info("Invalid SORT column name")
                throw Exception("Invalid SORT column name")
            }
        }
        val companies = companyService.getAllSortedByField(sortColumn)
        logger.info(companies.toString())
        return companies
    }

    @PutMapping("/find/")
    @ApiOperation("Get Product by ")
    fun getByExample(
        @RequestBody companyExample: CompanyExample
    ): List<CompanyDto> {
        logger.info("Get companies by example $companyExample")
        return companyService.getByExample(companyExample)
    }

    @PostMapping
    @ApiOperation("Create Company from DTO")
    @CacheEvict(value = ["companies"], allEntries = true)
    fun create(
        @Parameter(
            description = "DTO of Company."
        )
        @RequestBody companyDtoForCreate: CompanyDtoForCreate
    ): ResponseEntity<CompanyDto> {
        try {
            ValidatorCompanyDtoForCreate.validate(companyDtoForCreate)
        } catch (excp: Exception) {
            logger.error(excp.message)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, excp.message, excp)
        }
        val resultDto = companyService.createCompany(companyDtoForCreate)
        return ResponseEntity.ok(resultDto)
    }

    @DeleteMapping("/{n}")
    @ApiOperation("Delete Company by N")
    @CacheEvict(value = ["companies"], allEntries = true)
    fun delete(
        @Parameter(
            description = "N(ID) Company."
        )
        @PathVariable
        n: Long,
    ): String {
        return companyService.deleteCompany(n)
    }

    @PostMapping("/{n}")
    @ApiOperation("Update Company by N")
    @CacheEvict(value = ["companies"], allEntries = true)
    fun update(
        @Parameter(description = "N(ID) Company.")
        @PathVariable
        n: Long,
        @Parameter(description = "DTO of Company.")
        @RequestBody changedCompanyDto: CompanyDto,
    ): CompanyDto {
        ValidatorCompanyDto.validate(changedCompanyDto)
        return companyService.updateCompany(n, changedCompanyDto.name)
    }
}