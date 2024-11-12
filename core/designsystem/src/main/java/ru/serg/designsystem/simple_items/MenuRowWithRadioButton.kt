package ru.serg.designsystem.simple_items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.settingsSubText

@Composable
fun MenuRowWithRadioButton(
    optionName: String,
    modifier: Modifier = Modifier,
    descriptionText: String? = null,
    buttonState: Boolean = false,
    onSwitchClick: ((Boolean) -> Unit) = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {

        if (!descriptionText.isNullOrBlank()) {
            Column(
                modifier = modifier
                    .weight(1f)
            ) {
                Text(
                    text = optionName,
                    fontSize = 20.sp,
                    modifier = modifier
                )

                Text(
                    text = descriptionText,
                    modifier = modifier
                        .padding(top = 8.dp),
                    style = settingsSubText
                )
            }
        } else {
            Text(
                text = optionName,
                fontSize = 20.sp,
                modifier = modifier
                    .weight(1f)
            )
        }

        Box(
            modifier = modifier
                .padding(start = 12.dp)
        ) {

            Switch(
                checked = buttonState,
                onCheckedChange = {
                    onSwitchClick(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MenuRowWithRadioButton("Is Enabled")
}

@Preview(showBackground = true)
@Composable
fun PreviewDesc() {
    MenuRowWithRadioButton(
        "Is Enabled",
        descriptionText = "How we use it and what do you get if enable or disable this switch"
    )
}