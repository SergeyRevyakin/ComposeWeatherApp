package ru.serg.designsystem

import org.junit.Test
import ru.serg.designsystem.utils.emptyString
import ru.serg.designsystem.utils.firstLetterToUpperCase
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun testEmptyString() {
        val string = emptyString()
        assertEquals("", string)
    }

    @Test
    fun testFirstLetterToUpperCase() {
        val test = "test"
        val result = "Test"
        assertEquals(result, test.firstLetterToUpperCase())
    }

    @Test
    fun testNullFirstLetterToUpperCase() {
        val test = null
        val result = ""
        assertEquals(result, test.firstLetterToUpperCase())
    }

    @Test
    fun testNotLetterToUpperCase() {
        val test = "#!@"
        val result = "#!@"
        assertEquals(result, test.firstLetterToUpperCase())
    }
}