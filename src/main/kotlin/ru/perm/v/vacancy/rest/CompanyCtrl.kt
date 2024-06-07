package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.impl.CompanyService

@RestController
@RequestMapping("/company")
@Api(tags = ["Rest Company"])
class CompanyCtrl(val companyService: CompanyService) {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    @Cacheable("product_echo")
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String
    ): String {
        logger.info(java.lang.String.format("echo %s", mes))
        return mes
    }

    @GetMapping("/")
    @ApiOperation("Get all companies")
    fun getAll(): List<CompanyDto> {
        logger.info("Get all companies")
        val companies= companyService.getAllSortedByField("n")
        logger.info(companies.toString())
        return companies
    }

    @GetMapping("/sortByColumn/{column}")
    @ApiOperation("Get all companies")
    //TODO: add cache, rewrite with criteria search
    fun getAllSortByColumn(@PathVariable("column") column: String): List<CompanyDto> {
        var sortColumn ="n";
        logger.info("Get all companies")
        if (column.isNotEmpty()) {
            sortColumn=column
        }
        val companies= companyService.getAllSortedByField(sortColumn)
        logger.info(companies.toString())
        return companies
    }

    @GetMapping("/{n}")
    @ApiOperation("Get Product by N")
    @Cacheable("products")
    fun getByN(
        @Parameter(
            description = "N(ID) Product."
        )
        @PathVariable
        n: Long
    ): CompanyDto  {
        return companyService.getCompanyByN(n)
    }

}