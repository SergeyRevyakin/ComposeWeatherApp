package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuRowWithRadioButton(
    optionName: String,
    modifier: Modifier = Modifier,
//    hasDescription:Boolean = false,
    descriptionText: String? = null,
    buttonState: MutableState<Boolean> = mutableStateOf(false),
    onSwitchClick: ((Boolean) -> Unit) = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {

        if (!descriptionText.isNullOrBlank()) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = optionName,
                    fontSize = 20.sp,
                    modifier = modifier
                )

                Text(
                    text = descriptionText,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = modifier
                        .padding(top = 8.dp)
                )
            }
        } else {
            Text(
                text = optionName,
                fontSize = 18.sp,
                modifier = modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }

        Box(
            modifier = modifier
                .size(48.dp)
                .align(Alignment.CenterVertically)
        ) {

            Switch(
                checked = buttonState.value,
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