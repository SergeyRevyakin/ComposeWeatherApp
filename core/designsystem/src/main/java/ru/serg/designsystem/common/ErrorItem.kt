package ru.serg.designsystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.SentimentDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.gradientBorder
import ru.serg.strings.R.string

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val gradient = Brush.linearGradient(
            listOf(
                MaterialTheme.colors.background.copy(alpha = 0.8f)
                    .compositeOver(MaterialTheme.colors.onBackground),
                MaterialTheme.colors.background
            ),
        )

        Column(
            modifier = modifier
                .padding(12.dp)
                .shadow(
                    elevation = 10.dp,
                    spotColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .gradientBorder()
                .background(gradient)
                .fillMaxWidth()
                .wrapContentHeight(),
//                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = string.error_we_cant_show_you_a_weather),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            )

            Icon(
                imageVector = Icons.Sharp.SentimentDissatisfied,
                contentDescription = null,
                Modifier
                    .padding(end = 16.dp)
                    .size(72.dp),
                tint = MaterialTheme.colors.primary
            )

            Text(
                text = errorText
                    ?: stringResource(id = string.error_check_connection_or_try_again_later),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(24.dp),
            )

            if (onRefreshClick != null) {
                Text(
                    text = errorText
                        ?: stringResource(id = string.refresh),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp,
                                bottomEnd = 24.dp, bottomStart = 24.dp
                            )
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
        ErrorItem(errorText = "No connection!")
    }
}

@Preview
@Composable
fun PreviewErrorItemNoText() {
    val darkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(darkTheme) {
        ErrorItem(onRefreshClick = {})
    }
}