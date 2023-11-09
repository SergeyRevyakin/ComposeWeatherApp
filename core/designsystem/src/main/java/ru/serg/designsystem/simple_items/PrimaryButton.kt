package ru.serg.designsystem.simple_items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.emptyString

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    headerText: String = emptyString(),
    onClick: (() -> Unit)
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                MaterialTheme.colors.primary
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colors.onPrimary),
                RoundedCornerShape(16.dp)
            )
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = headerText,
            fontSize = 20.sp,
//            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCenteredButton() {
    ComposeWeatherAppTheme {
        PrimaryButton(
            headerText = "Pick color",
        ) {}
    }
}
