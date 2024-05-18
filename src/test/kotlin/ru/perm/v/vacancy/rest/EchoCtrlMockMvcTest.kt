package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(EchoCtrl::class)
class EchoCtrlMockMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getEchoCtrl() {
        val message = mockMvc.perform(get("/echo/MESSAGE"))
            .andExpect(status().isOk).andReturn().response.contentAsString
        assert(message == "MESSAGE")
    }
}