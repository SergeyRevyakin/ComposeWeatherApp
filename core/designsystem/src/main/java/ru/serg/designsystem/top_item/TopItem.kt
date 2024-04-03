package ru.serg.designsystem.top_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.strings.R.string

@Composable
fun TopItem(
    header: String,
    rightIconImageVector: ImageVector? = null,
    leftIconImageVector: ImageVector? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
    isLoading: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .padding(start = 16.dp, end = 24.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        onLeftIconClick?.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                leftIconImageVector?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = stringResource(id = string.accessibility_desc_search_icon),
                        modifier = Modifier
                    )
                }
            }

            Text(
                text = header,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )

            Column(
                Modifier
                    .padding(start = 24.dp, end = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .then(
                        if (rightIconImageVector != null) Modifier.clickable {
                            onRightIconClick?.invoke()
                        } else Modifier
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                rightIconImageVector?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = stringResource(id = string.accessibility_desc_settings_icon),
                    )
                }
            }
        }

        AttachedProgressBar(isLoading = isLoading)

        ConnectivityStatus()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopItem(
        header = "Moscow",
        leftIconImageVector = Icons.Rounded.Search,
        rightIconImageVector = Icons.Rounded.Settings,
        onLeftIconClick = {},
        onRightIconClick = {}
    )
}
