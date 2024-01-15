package ru.serg.widget_settings_feature.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.designsystem.utils.emptyString

@Composable
fun MenuCommonColorButton(
    headerText: String = emptyString(),
    descriptionText: String = emptyString(),
    color: Color = Color.White,
    onClick: (() -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                MaterialTheme.colors.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colors.onBackground),
                RoundedCornerShape(24.dp)
            )
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = headerText,
                fontSize = 20.sp,
            )

            if (descriptionText.isNotEmpty()) {
                Text(
                    text = descriptionText,
                    style = settingsSubText,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }

        ColorBoxItem(color = color)
    }
}


@Preview
@Composable
fun PreviewMenuCommonColorButton() {
    ComposeWeatherAppTheme {
        MenuCommonColorButton(
            headerText = "Right now application don't have access to your device location",
            descriptionText = "Tap if you want to turn it on"
        ) {}
    }
}

@Preview
@Composable
fun PreviewMenuCommonColorButtonNoDesc() {
    ComposeWeatherAppTheme {
        MenuCommonColorButton(
            headerText = "Right now application don't have access to your device location",
        ) {}
    }
}