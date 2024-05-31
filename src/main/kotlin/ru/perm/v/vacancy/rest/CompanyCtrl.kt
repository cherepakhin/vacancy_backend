package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.perm.v.vacancy.dto.CompanyDto
import ru.perm.v.vacancy.service.impl.CompanyService

@RestController
@RequestMapping("/company")
@Api(tags = ["Rest Company"])
class CompanyCtrl(val companyService: CompanyService) {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/")
    @ApiOperation("Get all companies")
    fun getAll(): List<CompanyDto> {
        logger.info("Get all companies")
        logger.info(companyService.getAll().toString())
        val companies= companyService.getAll()
        logger.info(companies.toString())
        return companies
    }
}