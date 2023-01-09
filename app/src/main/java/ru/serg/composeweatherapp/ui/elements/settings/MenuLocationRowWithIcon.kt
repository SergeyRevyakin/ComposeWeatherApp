package ru.serg.composeweatherapp.ui.elements.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOff
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuLocationRowWithIcon(
    isLocationAvailable: Boolean,
    onClick: (() -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Icon(
            imageVector = if (isLocationAvailable) Icons.Rounded.LocationOn
            else Icons.Rounded.LocationOff, contentDescription = "",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(48.dp)

        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = if (isLocationAvailable) "Right now application has access to your device location"
                else "Right now application don't have access to your device location",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = if (isLocationAvailable) "Tap if you want to turn it off"
                else "Tap if you want to turn it on",
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewMenuRowWithIcon() {
    MenuLocationRowWithIcon(true) {}
}