package ru.perm.v.vacancy.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.persistence.EntityManager
import javax.persistence.Query

import ru.perm.v.vacancy.consts.SqlScripts

/**
 * Controller for use with integration tests
 */
//TODO: check work
@RestController
@RequestMapping("/init")
@Api(tags = ["Controller for use with integration tests."])
class InitRest {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    var entityManager: EntityManager? = null

    @GetMapping("/echo/{mes}")
    @ApiOperation("echo message")
    fun echoStr(
        @PathVariable("mes")
        @ApiParam(name = "mes", value = "any string") mes: String,
    ): String {
        logger.info("$mes")
        return mes
    }

    @GetMapping("/reimport_db")
    @ApiOperation("Clear database WITH import.sql")
    @Transactional
    fun reInitDB(): String {
        logger.info("Init database")
        val inputStream: InputStream = this.javaClass.getResourceAsStream(SqlScripts.IMPORT_SQL)
        val initSql: String = readFromInputStream(inputStream)

        val initQuery: Query = entityManager!!.createNativeQuery(initSql);
        initQuery.executeUpdate()

        return "Ok"
    }

    @GetMapping("/empty_db")
    @ApiOperation("Clear database WITHOUT import.sql. All tables will be cleared.")
    @Transactional
    fun clearDB(): String {
        logger.info("Clear database")
        val inputStream: InputStream = this.javaClass.getResourceAsStream(SqlScripts.EMPTYDB_SQL)
        val emptySql: String = readFromInputStream(inputStream)
        logger.info("empty_db.sql: " + emptySql)
        val clearQuery: Query = entityManager!!.createNativeQuery(emptySql);
        clearQuery.executeUpdate()

        return "Ok"
    }

    @Throws(IOException::class)
    private fun readFromInputStream(inputStream: InputStream): String {
        val resultStringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).use {
                br,
            ->
            var line: String?
            while ((br.readLine().also { line = it }) != null) {
                resultStringBuilder.append(line).append("\n")
            }
        }
        return resultStringBuilder.toString()
    }
}
