package ru.serg.composeweatherapp.ui.elements.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.ui.theme.PreviewDarkTheme

@Composable
fun HourSliderItem(
    isVisible: Boolean = false,
    hours: List<Int> = listOf(1, 2, 4, 6, 8, 12, 24),
    value: Float = 2f,
    onValueChanged: ((Int) -> Unit) = {}
) {
    var stateValue by remember {
        mutableStateOf(value)
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(
            animationSpec = tween(600),
            expandFrom = Alignment.Top,
            initialHeight = { 0 }
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(300)
        ) + slideInVertically(
            initialOffsetY = { -200 },
            animationSpec = tween(600)
        ),
        exit = shrinkVertically(
            animationSpec = tween(600),
            shrinkTowards = Alignment.Top,
            targetHeight = { 0 }
        ) + fadeOut(
            targetAlpha = 0.3f,
            animationSpec = tween(300)
        ) + slideOutVertically(
            targetOffsetY = { -200 },
            animationSpec = tween(600)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "How often update weather data?",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
            val textSize = with(LocalDensity.current) { 14.sp.toPx() }
            val canvasHeight = 50.dp
            val textPaint = android.graphics.Paint().apply {
                color = MaterialTheme.colors.onBackground.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
                this.textSize = textSize
            }

            val primaryColor = MaterialTheme.colors.primary

            Box(contentAlignment = Alignment.Center) {
                Canvas(
                    modifier = Modifier
                        .height(canvasHeight)
                        .fillMaxWidth()
                ) {

                    val distance = (size.width.minus(2 * drawPadding)).div(hours.size.minus(1))
                    hours.forEachIndexed { index, hour ->
                        drawCircle(
                            color = primaryColor,
                            radius = 8f,
                            center = Offset(
                                x = drawPadding + index.times(distance),
                                y = size.height / 2
                            )
                        )
                        if (index.rem(2) == 0) {
                            this.drawContext.canvas.nativeCanvas.drawText(
                                hour.toString(),
                                drawPadding + index.times(distance),
                                size.height,
                                textPaint
                            )
                        }
                    }
                }

                Slider(
                    value = stateValue,
                    onValueChange = {
                        stateValue = it
                    },
                    onValueChangeFinished = {
                        onValueChanged.invoke(stateValue.toInt())
                    },
                    valueRange = 0f..hours.size.minus(1).toFloat(),
                    steps = hours.size.minus(2),
                    colors = SliderDefaults.colors(
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent

                    )
                )
            }

            Text(
                text = "Weather will be updated every ${hours[value.toInt()]} hours",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SliderPreview() {
    PreviewDarkTheme {
        HourSliderItem()
    }
}