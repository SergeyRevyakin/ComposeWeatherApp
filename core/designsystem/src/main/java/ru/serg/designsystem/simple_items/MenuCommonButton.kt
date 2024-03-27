package ru.serg.designsystem.simple_items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.designsystem.utils.emptyString

@Composable
fun MenuCommonButton(
    headerText: String = emptyString(),
    descriptionText: String = emptyString(),
    onClick: (() -> Unit)
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                MaterialTheme.colorScheme.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colorScheme.onBackground),
                RoundedCornerShape(24.dp)
            )
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Text(
            text = headerText,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        if (descriptionText.isNotEmpty()) {
            Text(
                text = descriptionText,
                style = settingsSubText,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Preview
@Composable
fun PreviewMenuCommonButton() {
    ComposeWeatherAppTheme {
        MenuCommonButton(
            headerText = "Right now application don't have access to your device location",
            descriptionText = "Tap if you want to turn it on"
        ) {}
    }
}

@Preview
@Composable
fun PreviewMenuCommonButtonNoDesc() {
    ComposeWeatherAppTheme {
        MenuCommonButton(
            headerText = "Right now application don't have access to your device location",
        ) {}
    }
}