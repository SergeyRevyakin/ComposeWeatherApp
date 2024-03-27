package ru.serg.settings_feature.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.PreviewDarkTheme
import ru.serg.strings.R.string
import kotlin.math.roundToInt

@Composable
fun HourSliderItem(
    hours: List<Int> = listOf(1, 2, 4, 6, 8, 12, 24),
    stateValue: MutableState<Float> = mutableFloatStateOf(2f),
    onValueChanged: ((Int) -> Unit) = {}
) {

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = string.how_often_update_weather_data),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
        val textSize = with(LocalDensity.current) { 14.sp.toPx() }
        val canvasHeight = 50.dp
        val textPaint = android.graphics.Paint().apply {
            color = MaterialTheme.colorScheme.onBackground.toArgb()
            textAlign = android.graphics.Paint.Align.CENTER
            this.textSize = textSize
        }

        val primaryColor = MaterialTheme.colorScheme.primary

        Box(contentAlignment = Alignment.Center) {
            Slider(
                value = stateValue.value,
                onValueChange = {
                    stateValue.value = it
                },
                onValueChangeFinished = {
                    onValueChanged.invoke(stateValue.value.toInt())
                },
                valueRange = 0f..hours.size.minus(1).toFloat(),
                steps = hours.size.minus(2),
                colors = SliderDefaults.colors(
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent

                )
            )

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
        }

        Text(
            text = stringResource(
                id = string.weather_will_be_updated_every_value,
                hours[stateValue.value.roundToInt()]
            ),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderPreview() {
    PreviewDarkTheme {
        HourSliderItem()
    }
}