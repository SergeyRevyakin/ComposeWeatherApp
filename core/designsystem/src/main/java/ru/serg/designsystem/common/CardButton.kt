package ru.serg.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.gradientBorder
import ru.serg.designsystem.utils.Constants.CARD_BUTTON_TEST_TAG
import ru.serg.strings.R.string

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
                contentDescription = stringResource(id = string.accessibility_desc_search_icon),
                Modifier
                    .size(48.dp)
                    .testTag(CARD_BUTTON_TEST_TAG),
                tint = MaterialTheme.colorScheme.primary,
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