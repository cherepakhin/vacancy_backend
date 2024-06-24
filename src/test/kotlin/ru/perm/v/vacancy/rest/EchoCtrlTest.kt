package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EchoCtrlTest {

    @Test
    fun echoStr() {
        val ctrl = EchoCtrl()
        assertEquals("message", ctrl.echoStr("message"))
    }
}