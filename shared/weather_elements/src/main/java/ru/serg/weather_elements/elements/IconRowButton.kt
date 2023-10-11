package ru.serg.weather_elements.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.buttonText

@Composable
fun IconRowButton(
    image: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button
            ) {
                onClick()
            }
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            imageVector = image,
            contentDescription = "",
            modifier = Modifier.padding(12.dp),
            tint = MaterialTheme.colors.primary
        )

        Text(
            text = text,
            style = buttonText,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewIconRowButton() {
    ComposeWeatherAppTheme {

        IconRowButton(
            Icons.Default.Search,
            "Search"
        ) {

        }
    }
}