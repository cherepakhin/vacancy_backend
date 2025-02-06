package ru.perm.v.vacancy.validators

import ru.perm.v.vacancy.dto.CompanyDto
import javax.validation.ConstraintViolation
import javax.validation.Validation

object ValidatorCompanyDto {
    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    fun validate(companyDto: CompanyDto) {
        val violations: MutableSet<ConstraintViolation<CompanyDto>> = validator.validate(companyDto)

        if (violations.isNotEmpty()) {
            var messageError = ""
// OLD STYLE
//            for(violation in violations) {
//                messageError = messageError.plus(violation.message + "\n")
//            }

// USED STREAM
            violations.forEach { violation ->
                messageError = messageError.plus(violation.message + "\n")
            }
            throw Exception("$companyDto has errors: $messageError")
        }
    }
}