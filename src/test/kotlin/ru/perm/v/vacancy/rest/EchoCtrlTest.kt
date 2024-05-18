package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EchoCtrlTest {

    @Test
    fun echoStr() {
        val ctrl = EchoCtrl()
        assertEquals("message", ctrl.echoStr("message"))
    }
}