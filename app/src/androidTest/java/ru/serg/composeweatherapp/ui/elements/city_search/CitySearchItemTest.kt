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

class CitySearchItemTest : ComposeElementTest() {

    @Test
    fun testCitySearchItem() = runTest {
        var onDeleteClickCounter = 0
        var onItemClickCounter = 0

        setContent {
            CitySearchItem(cityItem = getTestCityItem(),
                onDelete = { onDeleteClickCounter++ },
                onItemClick = { onItemClickCounter++ })
        }

        val cityNameNode = onNodeWithText(getTestCityItem().name)
        val deleteButton = onNodeWithTag(Constants.TestTag.CITY_SEARCH_DELETE_TEST_TAG, useUnmergedTree = true)

        cityNameNode.assertIsDisplayed()
        cityNameNode.performClick()

        deleteButton.apply {
            assertIsDisplayed()
            performClick()
            performClick()
        }

        Assert.assertEquals(2, onDeleteClickCounter)
        Assert.assertEquals(1, onItemClickCounter)

    }
}