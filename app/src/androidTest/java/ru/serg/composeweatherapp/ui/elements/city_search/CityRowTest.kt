package ru.serg.composeweatherapp.ui.elements.city_search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Test
import ru.serg.composeweatherapp.getTestCityItem
import ru.serg.composeweatherapp.ui.ComposeElementTest
import ru.serg.composeweatherapp.utils.Constants

class CityRowTest : ComposeElementTest() {

    @Test
    fun testCityRow() = runTest {
        var onItemClickCount = 0
        var onAddClickCount = 0
        setContent {
            CityRow(cityItem = getTestCityItem(),
                onItemClick = { onItemClickCount++ },
                onAddClick = { onAddClickCount++ })
        }

        val cityNode = onNodeWithText("${getTestCityItem().name}, ${getTestCityItem().country}")
        val addButton = onNodeWithTag(Constants.TestTag.CITY_ROW_TEST_TAG, useUnmergedTree = true)

        cityNode.assertIsDisplayed()
        addButton.assertIsDisplayed()

        cityNode.performClick()
        addButton.performClick()
        addButton.performClick()

        Assert.assertEquals(1, onItemClickCount)
        Assert.assertEquals(2, onAddClickCount)
    }
}