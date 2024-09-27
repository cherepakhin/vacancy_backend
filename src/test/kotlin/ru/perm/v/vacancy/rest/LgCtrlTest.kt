package ru.perm.v.vacancy.rest

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class LgCtrlTest {
    @Test
    fun getLog() {
        val ctrl = LogCtrl()
        assertTrue(ctrl.getLog().length > 0)
    }
}