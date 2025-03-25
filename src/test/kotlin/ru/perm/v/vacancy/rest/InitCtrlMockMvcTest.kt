package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Query

@WebMvcTest(controllers = [InitCtrl::class])
class InitCtrlMockMvcTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var entityManagerFactory: EntityManagerFactory

    @MockBean
    lateinit var entityManager: EntityManager

    @Test
    fun echoStr() {
        val initQuery = mock(Query::class.java)
        `when`(entityManager.createNativeQuery(any())).thenReturn(initQuery)

        val message = mockMvc.perform(get("/init/echo/MESSAGE"))
            .andExpect(status().isOk).andReturn().response.contentAsString

        assertEquals("MESSAGE", message)
    }
}