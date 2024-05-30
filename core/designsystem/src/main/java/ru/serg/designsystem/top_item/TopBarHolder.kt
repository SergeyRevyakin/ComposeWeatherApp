@file:OptIn(ExperimentalMaterial3Api::class)

package ru.serg.designsystem.top_item

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.serg.designsystem.utils.Constants

@Stable
data class TopBarHolder(
    val header: String,
    val rightIconImageVector: ImageVector? = null,
    val leftIconImageVector: ImageVector? = null,
    val onLeftIconClick: (() -> Unit)? = null,
    val onRightIconClick: (() -> Unit)? = null,
    val leftIconDescription: String = Constants.EMPTY_STRING,
    val rightIconDescription: String = Constants.EMPTY_STRING,
    val appBarState: TopAppBarScrollBehavior?,
)
