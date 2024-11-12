package ru.serg.designsystem.simple_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.utils.emptyString

@Composable
fun MenuSettingsRowWithIcon(
    iconImageVector: ImageVector,
    headerText: String = emptyString(),
    descriptionText: String = emptyString(),
    onClick: (() -> Unit)
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                MaterialTheme.colorScheme.surface
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colorScheme.onBackground),
                RoundedCornerShape(24.dp)
            )
            .padding(12.dp)

    ) {
        Icon(
            imageVector = iconImageVector, contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)

        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = headerText,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = descriptionText,
                color = MaterialTheme.colorScheme.primary,
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
    MenuSettingsRowWithIcon(
        iconImageVector = Icons.Rounded.LocationOff,
        headerText = "Right now application don't have access to your device location",
        descriptionText = "Tap if you want to turn it on"
    ) {}
}