package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.perm.v.vacancy.consts.VacancyColumns
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.service.VacancyService
import ru.perm.v.vacancy.service.impl.CompanyService
import ru.perm.v.vacancy.validators.ValidatorVacancyDtoForCreate

@RestController
@RequestMapping("/vacancy")
@Api(tags = ["Rest Vacancy"])
class VacancyCtrl() {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var vacancyService: VacancyService

    @Autowired
    lateinit var companyService: CompanyService

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
        //TODO: realize
        return vacancyService.getAll()
//        if (!VacancyColumns.values().contains(column)) {
//            throw IllegalArgumentException("Invalid column name")
//        }
//        return vacancyService.getAllSortedByField()
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
        @RequestBody vacancyDto: VacancyDtoForCreate,
    ): VacancyDto {
        ValidatorVacancyDtoForCreate.validate(vacancyDto)
        companyService.getCompanyByN(vacancyDto.company_n) // throw is company not exists
        return vacancyService.create(vacancyDto)
    }
    //TODO: delete by id
}