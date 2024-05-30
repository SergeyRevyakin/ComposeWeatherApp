@file:OptIn(ExperimentalMaterial3Api::class)

package ru.serg.designsystem.top_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    holder: TopBarHolder,
    isLoading: Boolean = false
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = holder.header,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            },
            navigationIcon = {
                if (holder.onLeftIconClick != null && holder.leftIconImageVector != null) {
                    IconButton(onClick = holder.onLeftIconClick) {
                        Icon(
                            imageVector = holder.leftIconImageVector,
                            contentDescription = holder.leftIconDescription
                        )
                    }
                }
            },
            actions = {
                if (holder.onRightIconClick != null && holder.rightIconImageVector != null) {
                    IconButton(onClick = holder.onRightIconClick) {
                        Icon(
                            imageVector = holder.rightIconImageVector,
                            contentDescription = holder.rightIconDescription
                        )
                    }
                }
            },
            scrollBehavior = holder.appBarState,
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { -it }) + slideInVertically(
                animationSpec = tween(200),
                initialOffsetY = { -it }),
            exit = slideOutHorizontally(
                animationSpec = tween(300),
                targetOffsetX = { it }) + slideOutVertically(
                animationSpec = tween(200),
                targetOffsetY = { it })
        ) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }

        ConnectivityStatus()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        holder = TopBarHolder(
            header = "London",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
            rightIconImageVector = Icons.Rounded.Settings,
            onLeftIconClick = {},
            onRightIconClick = {},
            appBarState = null
        ),
        isLoading = true
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOneButtonTopBar() {
    TopBar(
        holder = TopBarHolder(
            header = "London",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
            rightIconImageVector = null,
            onLeftIconClick = {},
            onRightIconClick = {},
            appBarState = null
        ),
        isLoading = true
    )
}
