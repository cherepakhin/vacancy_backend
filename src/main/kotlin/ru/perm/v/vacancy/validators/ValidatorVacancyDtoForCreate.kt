package ru.perm.v.vacancy.validators

import ru.perm.v.vacancy.dto.VacancyDtoForCreate
import javax.validation.Validation

object ValidatorVacancyDtoForCreate {
    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    fun validate(vacancyDtoForCreate: VacancyDtoForCreate) {
        val violations = validator.validate(vacancyDtoForCreate).toList()
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
            throw Exception("$vacancyDtoForCreate has errors: $messageError")
        }
    }
}