package ru.perm.v.vacancy.validators

import ru.perm.v.vacancy.dto.VacancyDto
import javax.validation.ConstraintViolation
import javax.validation.Validation

object ValidatorVacancyDto {
    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    fun validate(vacancyDto: VacancyDto) {
        val violations: MutableSet<ConstraintViolation<VacancyDto>> = validator.validate(vacancyDto)

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
            throw Exception("$vacancyDto has errors: $messageError")
        }
    }

// Don`t delete comment
//    fun validateForCreate(vacancyDto: VacancyDtoForCreate) {
//        val violations: MutableSet<ConstraintViolation<VacancyDto>> = validator.validate(vacancyDto)
//
//        if (violations.isNotEmpty()) {
//            var messageError = ""
//// OLD STYLE
////            for(violation in violations) {
////                messageError = messageError.plus(violation.message + "\n")
////            }
//
//// USED STREAM
//            violations.forEach { violation ->
//                messageError = messageError.plus(violation.message + "\n")
//            }
//            throw Exception("$vacancyDto has errors: $messageError")
//        }
//    }

}