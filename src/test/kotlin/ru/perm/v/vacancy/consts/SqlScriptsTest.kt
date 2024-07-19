package ru.perm.v.vacancy.consts

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SqlScriptsTest {
    @Test
    fun checkNameImportSql() {
        assertEquals("/import.sql", SqlScripts.IMPORT_SQL)
    }

    @Test
    fun checkNameEmptySql() {
        assertEquals("/empty_db.sql", SqlScripts.EMPTYDB_SQL)
    }
}
