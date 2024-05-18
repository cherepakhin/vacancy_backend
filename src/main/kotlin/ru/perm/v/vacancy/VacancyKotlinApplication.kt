package ru.perm.v.vacancy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class VacancyKotlinApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<VacancyKotlinApplication>(*args)
        }
    }
}

