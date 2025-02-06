package ru.perm.v.vacancy.consts

import org.slf4j.LoggerFactory
import kotlin.test.Test

class LogTest {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @Test
    fun test() {
        val n = 10
        logger.info("getNextN(): $n")
    }
}