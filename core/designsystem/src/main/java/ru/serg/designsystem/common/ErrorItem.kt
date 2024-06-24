package ru.serg.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.plugins.ClientRequestException
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.strings.R.string

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    throwable: Throwable? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = modifier
                .padding(16.dp)
                .padding(bottom = 64.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Sharp.Cancel,
                contentDescription = null,
                Modifier
                    .padding(top = 32.dp)
                    .size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = string.error_we_cant_show_you_a_weather),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            )

            val exceptionText = (throwable as? ClientRequestException)?.response?.status

            Text(
                text = exceptionText?.let { stringResource(string.exception_details, it) }
                    ?: errorText
                    ?: stringResource(id = string.error_check_connection_or_try_again_later),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(12.dp)
                    .padding(bottom = 12.dp),
            )

            if (onRefreshClick != null) {
                Text(
                    text = stringResource(id = string.refresh),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .clickable {
                            onRefreshClick()
                        }
                        .padding(vertical = 24.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewErrorItem() {
    ComposeWeatherAppTheme {
        Scaffold {
            ErrorItem(errorText = "No connection!", modifier = Modifier.padding(it))
        }
    }
}

@Preview
@Composable
fun PreviewErrorItemNoText() {
    val darkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(darkTheme) {
        Scaffold {
            ErrorItem(onRefreshClick = {}, modifier = Modifier.padding(it))
        }
    }
}