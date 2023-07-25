package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.ui.theme.ComposeWeatherAppTheme

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
        Icon(
            painter = painterResource(id = paramIcon),
            contentDescription = "Direction",
            modifier = Modifier
                .size(42.dp)
                .rotate(rotation.toFloat())
        )

        Text(
            text = paramValue,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 18.dp)
        )

        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info",
            tint = if (hasInfoButton) MaterialTheme.colors.primary else Color.Transparent,
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
            paramIcon = R.drawable.ic_wind_dir_north,
            paramValue = "Wind speed: 12m/s",
        )
    }
}

@Preview(name = "with info")
@Composable
fun PreviewParamRowInfoItem() {
    ComposeWeatherAppTheme {
        ParamRowWithInfoItem(
            paramIcon = R.drawable.ic_wind_dir_north,
            paramValue = "UV Index: Low",
            hasInfoButton = true
        )
    }
}