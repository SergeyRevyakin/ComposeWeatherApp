package ru.serg.weather_elements.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.util.date.getTimeMillis
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.customColors
import ru.serg.model.AlertItem
import ru.serg.strings.R.string
import ru.serg.weather_elements.getFormattedLastUpdateDate

@Composable
fun AlertCardItem(
    alertItem: AlertItem,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 24.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background
                    .copy(alpha = 0.9f)
                    .compositeOver(MaterialTheme.colorScheme.onBackground),
                RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Error,
                tint = MaterialTheme.customColors.red,
                contentDescription = "warning icon",
                modifier = Modifier
                    .padding()
                    .size(32.dp)

            )

            Text(
                text = alertItem.title,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth()
            )

        }

        ExpandableText(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = alertItem.description,
            style = LocalTextStyle.current.copy(
                fontSize = 16.sp
            ),
            showMoreStyle = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(string.starts_at),
                    fontSize = 16.sp,
                    modifier = Modifier
                )
                Text(
                    text = getFormattedLastUpdateDate(alertItem.startAt),
                    fontSize = 16.sp,
                    modifier = Modifier
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(string.end_at),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    modifier = Modifier
                )
                Text(
                    text = getFormattedLastUpdateDate(alertItem.endAt),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    modifier = Modifier
                )
            }

        }
    }
}


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = 3,
    showMoreText: String = " Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = Modifier
        .clickable(
            enabled = clickable,
            indication = null,
            interactionSource = interactionSource
        ) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        append("...")
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
    }

}

@Preview
@Composable
private fun PreviewAlert() {
    val alert = AlertItem(
        title = "Moderate rain warning",
        description = "One-hour accumulated precipitation: 20 mm.",
        startAt = getTimeMillis() - 60 * 2 * 1000,
        endAt = getTimeMillis() + 60 * 2 * 1000,
    )
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            AlertCardItem(
                alert,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAlertLight() {
    val alert = AlertItem(
        title = "Moderate rain warning",
        description = "One-hour accumulated precipitation: 20 mm.",
        startAt = getTimeMillis() - 60 * 2 * 1000,
        endAt = getTimeMillis() + 60 * 2 * 1000,
    )
    val isDarkTheme = remember {
        mutableStateOf(false)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        Scaffold {
            AlertCardItem(
                alert,
                modifier = Modifier.padding(it)
            )
        }
    }
}