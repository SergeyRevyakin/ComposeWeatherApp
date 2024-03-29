package ru.serg.designsystem.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Test
import ru.serg.core.testing.ComposeElementTest
import ru.serg.designsystem.utils.Constants.CARD_BUTTON_TEST_TAG

class CardButtonTest : ComposeElementTest() {

    companion object {
        private const val TEST_TEXT = "Test Text"
        private val ICON = Icons.Rounded.Notifications
    }

    @Test
    fun testCardButton() = runTest {
        var clickCounter = 0
        setContent {
            CardButton(buttonText = TEST_TEXT, image = ICON) {
                clickCounter++
            }
        }

        val cardText = onNodeWithText(TEST_TEXT)
        val cardIcon = onNodeWithTag(CARD_BUTTON_TEST_TAG, useUnmergedTree = true)

        cardIcon.assertIsDisplayed()
        cardText.apply {
            assertIsDisplayed()
            performClick()
            performClick()
        }

        Assert.assertEquals(2, clickCounter)
    }
}