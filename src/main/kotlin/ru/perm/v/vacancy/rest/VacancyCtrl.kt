package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.service.VacancyService

@RestController
@RequestMapping("/vacancy")
@Api(tags = ["Rest Vacancy"])
class VacancyCtrl(val vacancyService: VacancyService) {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val validSortColumns = listOf("n", "name")

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    @Cacheable("product_echo")
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
    @ApiOperation("Get all vacancies")
    fun getAll(): List<VacancyDto> {
        logger.info("Get all companies")
        val vacancies = vacancyService.getAll()
        logger.info(vacancies.toString())
        return vacancies
    }

    @GetMapping("/sortByColumn/{column}")
    @ApiOperation("Get all companies")
    //TODO: add cache
    // rewrite with criteria search???
    fun getAllSortByColumn(@PathVariable("column") column: String): List<VacancyDto> {
        //TODO: write code getAllSortByColumn(column)
        return listOf()
    }

    @GetMapping("/{n}")
    @ApiOperation("Get Vacancy by N")
    @Cacheable("vacancies")
    fun getByN(
        @Parameter(
            description = "N(ID) Vacancy."
        )
        @PathVariable
        n: Long,
    ): VacancyDto {
        return vacancyService.getByN(n)
    }

    @PostMapping
    @ApiOperation("Create Vacancy")
    fun create(
        @Parameter(
            description = "DTO of Vacancy"
        )
        @RequestBody vacancyDto: VacancyDto,
    ): VacancyDto {
//        ValidatorCompanyDto.validate(companyDto)
//        return companyService.createCompany(companyDto)
        return VacancyDto()
    }
    //TODO: delete by id
}