package ru.serg.designsystem.top_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.plugins.ClientRequestException
import ru.serg.designsystem.theme.customColors
import ru.serg.strings.R.string

@Composable
fun ErrorTopBarItem(
    throwable: Throwable
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.customColors.red,
                shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
            )
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.CloudOff,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(28.dp)

            )

            Text(
                text = stringResource(string.not_able_to_fetch),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .padding(end = 32.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )

        }

        (throwable as? ClientRequestException)?.let {
            Text(
                text = stringResource(string.exception_details, it.response.status),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewErrorTopBarItem() {
    ErrorTopBarItem(
        Exception()
    )
}