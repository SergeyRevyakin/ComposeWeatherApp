package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.gradientBorder

@Composable
fun CardButton(
    buttonText: String,
    image: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .gradientBorder(cornerRadius = 12)
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(12.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = image,
                contentDescription = stringResource(id = R.string.accessibility_desc_search_icon),
                Modifier.size(48.dp),
                tint = MaterialTheme.colors.primary
            )

            Text(
                text = buttonText,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewCardButton() {
    CardButton(
        "Find city manually", Icons.Rounded.Search
    ) { }
}