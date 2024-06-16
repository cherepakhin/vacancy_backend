package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.impl.CompanyService
import ru.perm.v.vacancy.validators.ValidatorCompanyDto

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
        logger.info(java.lang.String.format("echo %s", mes))
        return mes
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
    @ApiOperation("Get all companies")
    // rewrite with criteria search???
    fun getAllSortByColumn(@PathVariable("column") column: String): List<CompanyDto> {
        var sortColumn = "n";
        logger.info("Get all companies")
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

    @PostMapping
    @ApiOperation("Create Company from DTO")
    @CacheEvict(value = ["companies"], allEntries = true)
    fun create(
        @Parameter(
            description = "DTO of Company."
        )
        @RequestBody companyDto: CompanyDto,
    ): CompanyDto {
        ValidatorCompanyDto.validate(companyDto)
        return companyService.createCompany(companyDto)
    }
    //TODO: delete by id
}