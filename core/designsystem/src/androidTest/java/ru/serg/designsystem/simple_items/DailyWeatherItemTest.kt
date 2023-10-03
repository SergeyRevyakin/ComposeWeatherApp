package ru.serg.designsystem.simple_items

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Test
import ru.serg.core.testing.ComposeElementTest
import ru.serg.designsystem.utils.Constants
import ru.serg.designsystem.utils.MockItems
import ru.serg.model.enums.Units

class DailyWeatherItemTest : ComposeElementTest() {
    @Test
    fun test_DailyWeatherItem_clicker() = runTest {
        var clickCounter = 0
        setContent {
            DailyWeatherItem(item = MockItems.getDailyWeatherMockItem(), units = Units.METRIC) {
                clickCounter++
            }
        }

        val item = onNodeWithTag(Constants.DAILY_WEATHER_ITEM_TEST_TAG, useUnmergedTree = true)

        item.apply {
            performClick()
            assertIsDisplayed()
        }

    }
}