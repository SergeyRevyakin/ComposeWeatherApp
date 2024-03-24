package ru.serg.designsystem.simple_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.utils.AnimWeather
import ru.serg.drawables.R.drawable
import ru.serg.strings.R.string

@Composable
fun ParamRowWithInfoItem(
    modifier: Modifier = Modifier,
    paramIcon: Int,
    paramValue: String,
    rotation: Int = 0,
    hasInfoButton: Boolean = false,
    onInfoClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                enabled = hasInfoButton,
                onClick = onInfoClick
            )
            .padding(6.dp)
            .fillMaxWidth()
    ) {

        AnimWeather(targetState = paramIcon) {
            Icon(
                painter = painterResource(id = it),
                contentDescription = stringResource(id = string.accessibility_desc_description_icon),
                modifier = Modifier
                    .size(42.dp)
                    .rotate(rotation.toFloat())
            )
        }

        AnimWeather(targetState = paramValue) {
            Text(
                text = it,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
        }

        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(id = string.accessibility_desc_info_icon),
            tint = if (hasInfoButton) MaterialTheme.colorScheme.primary else Color.Transparent,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)

        )
    }
}

@Preview
@Composable
fun PreviewParamRowWithInfoItem() {
    ComposeWeatherAppTheme {
        ParamRowWithInfoItem(
            paramIcon = drawable.ic_wind_dir_north,
            paramValue = "Wind speed: 12m/s",
        )
    }
}

@Preview(name = "with info")
@Composable
fun PreviewParamRowInfoItem() {
    ComposeWeatherAppTheme {
        ParamRowWithInfoItem(
            paramIcon = drawable.ic_wind_dir_north,
            paramValue = "UV Index: Low",
            hasInfoButton = true
        )
    }
}