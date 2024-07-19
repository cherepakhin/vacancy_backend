package ru.perm.v.vacancy

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.test.assertTrue


class ReadImportSqlTest {
    @Test
    fun read() {
        val inputStream: InputStream = this.javaClass.getResourceAsStream("/import.sql")
        val fileContent: String = readFromInputStream(inputStream)

        assertTrue(fileContent.length > 0)
    }

    @Test
    fun testReadForKotlin() {
        val fileContent = this::class.java.getResource("/import.sql").readText()

        assertTrue(fileContent.length > 0)
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

    @Test
    fun readWithObject() {
        val fileContent = object {}.javaClass.getResource("/import.sql")?.readText()
        println(fileContent)

        assertTrue((fileContent?.length ?: 0) > 0)
    }
}