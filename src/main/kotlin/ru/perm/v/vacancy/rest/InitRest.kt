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
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query


/**
 * Controller for use with integration tests
 */
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
        @ApiParam(name = "mes", value = "any string") mes: String
    ): String {
        logger.info("$mes")
        return mes
    }

    @GetMapping("/db")
    @ApiOperation("Init database")
    @Transactional
    fun reInitDB(): String {
        logger.info("Init database")
        val sqlScript ="delete from vacancy; delete from contact; delete from company;"
        val q: Query = entityManager!!.createNativeQuery(sqlScript);
        q.executeUpdate()
        return "Ok"
    }
}
