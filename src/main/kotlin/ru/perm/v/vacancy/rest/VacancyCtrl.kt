package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.CacheControl
import org.springframework.web.bind.annotation.*
import ru.perm.v.vacancy.consts.VacancyColumn
import ru.perm.v.vacancy.dto.VacancyDto
import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import ru.perm.v.vacancy.filter.VacancyExample
import ru.perm.v.vacancy.service.VacancyService
import ru.perm.v.vacancy.service.impl.CompanyService
import ru.perm.v.vacancy.validators.ValidatorVacancyDtoForCreate
import java.lang.String.format
import java.util.concurrent.TimeUnit


@RestController
@RequestMapping("/vacancy")
@Api(tags = ["Rest Vacancy"])
class VacancyCtrl() {
    var cacheControl: CacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
        .noTransform()
        .mustRevalidate()

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var vacancyService: VacancyService

    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    @Cacheable("vacancy_echo") // for example and simple test
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String,
    ): String {
        logger.info(java.lang.String.format("echo %s", mes))
        return mes
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

    @GetMapping("/")
    @ApiOperation("Get all vacancies")
    fun getAll(): List<VacancyDto> {
        logger.info("Get all companies")
        val vacancies = vacancyService.getAll()
        logger.info(vacancies.toString())
        return vacancies
    }

    @GetMapping("/sortByColumn/{column}")
    @ApiOperation("Get all vacancies sorted by column (n, name, company_n)")
    @Cacheable(value = ["column"], key = "#column")
    fun getAllSortByColumn(@ApiParam("Sort column") @PathVariable("column") column: String): List<VacancyDto> {
        val sortColumn = column.uppercase()
        try {
            VacancyColumn.valueOf(sortColumn).value
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(format("Invalid sort column: %s", column))
        }
        return vacancyService.getAllSortedByField(VacancyColumn.valueOf(sortColumn))
    }

    @GetMapping("/find/")
    @ApiOperation("Get all vacancies sorted by column (n, name, company_n)")
    fun getByExample(vacancyExample: VacancyExample): List<VacancyDto> {
        return vacancyService.getByExample(vacancyExample)
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

    @DeleteMapping("/{n}")
    @ApiOperation("Delete Vacancy by N")
    @Throws(Exception::class)
    //TODO: cache evict
    fun delete(n: Long): String {
        requireNotNull(vacancyService.getByN(n)) { format("Vacancy with N=%s not found", n) }
        try {
            vacancyService.delete(n)
            return "OK"
        } catch (e: Exception) {
            logger.error(e.message)
            return e.message.toString()
        }
    }

    @PostMapping("/{n}")
    @ApiOperation("Update Company by N")
//TODO:    @CacheEvict(value = ["vacancies"], allEntries = true)
    fun update(
        @Parameter(description = "N(ID) Vacancy.")
        @PathVariable
        n: Long,
        @Parameter(description = "DTO of Vacancy.")
        @RequestBody changedVacancyDto: VacancyDto,
    ): VacancyDto {
        return vacancyService.update(n, changedVacancyDto)
    }

}
