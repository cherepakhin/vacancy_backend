package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(LogCtrl::class)
class LogCtrlMockMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getLogStatus() {
        mockMvc.perform(MockMvcRequestBuilders.get("/log/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun getLogResult() {
        val message = mockMvc.perform(MockMvcRequestBuilders.get("/log/"))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn().response.contentAsString
        assertTrue(message.length > 0)
    }
}