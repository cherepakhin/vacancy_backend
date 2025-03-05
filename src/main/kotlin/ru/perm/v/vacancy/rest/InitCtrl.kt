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
import javax.persistence.EntityManagerFactory
import javax.persistence.FlushModeType
import javax.persistence.Persistence
import javax.persistence.PersistenceContext
import javax.persistence.PersistenceUnit

/**
 * Controller for use with integration tests
 */
@RestController
@RequestMapping("/init")
@Api(tags = ["Controller for use with integration tests."])
class InitCtrl {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @PersistenceContext
    var entityManager: EntityManager? = null

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    fun echoStr(
        @PathVariable("mes")
        @ApiParam(name = "mes", value = "any string") mes: String,
    ): String {
        logger.info("Echo string: $mes")
        return mes
    }

    @GetMapping("/reimport_db")
    @ApiOperation("Clear database and load test data with import.sql")
    @Transactional
    fun reInitDB(): String {
        logger.info("Init database")
        val inputStream: InputStream = this.javaClass.getResourceAsStream(SqlScripts.IMPORT_SQL)
        val initSql: String = readFromInputStream(inputStream)
        logger.info("import.sql: $initSql")

        if(entityManager == null) {
            logger.error("Entity manager is null.")
        }
// Не нужен. Метод помечен @Transactional
//        entityManager!!.transaction.begin()
        val initQuery: Query = entityManager!!.createNativeQuery(initSql)

// Не нужен. Метод помечен @Transactional
//        initQuery.setFlushMode(FlushModeType.COMMIT)
        logger.info("initQuery.toString():")

        val result = initQuery.executeUpdate()
        logger.info("Init result: $result") // Init result: 0
// Не нужен. Метод помечен @Transactional
//        entityManager!!.flush()
//        entityManager!!.transaction.commit()

        return "Ok"
    }

    @GetMapping("/empty_db")
    @ApiOperation("Clear database WITHOUT import.sql. All tables will be cleared.")
    @Transactional
    fun clearDB(): String {
        logger.info("Clear database")
        val inputStream: InputStream = this.javaClass.getResourceAsStream(SqlScripts.EMPTYDB_SQL)
        val emptySql: String = readFromInputStream(inputStream)
        logger.info("empty_db.sql: $emptySql")
        val clearQuery: Query = entityManager!!.createNativeQuery(emptySql)
        clearQuery.executeUpdate()

        return "Ok"
    }

    /**
     * Чтение содержимого InputStream в строку
     */
    @Throws(IOException::class)
    private fun readFromInputStream(inputStream: InputStream): String {
        val resultStringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).use {
                br,
            ->
            var line: String?
            while ((br.readLine().also { line = it }) != null) {
                resultStringBuilder.append(line).append("")
            }
        }
        return resultStringBuilder.toString()
    }
}
