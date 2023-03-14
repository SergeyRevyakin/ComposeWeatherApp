package ru.serg.composewetherapp.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.serg.composeweatherapp.utils.emptyString
import ru.serg.composeweatherapp.utils.firstLetterToUpperCase
import ru.serg.composeweatherapp.utils.hoursToMilliseconds

class ExtTest {

    @Test
    fun testEmptyString() {
        val string = emptyString()
        assertEquals("", string)
    }

    @Test
    fun testOneHourToMilliseconds() {
        val millisInHour = 60L * 60L * 1000L
        val oneHour = 1
        val result = oneHour * millisInHour
        assertEquals(oneHour.hoursToMilliseconds(), result)
    }

    @Test
    fun testFiveHoursToMilliseconds() {
        val millisInHour = 60L * 60L * 1000L
        val hours = 5
        val result = hours * millisInHour
        assertEquals(hours.hoursToMilliseconds(), result)
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