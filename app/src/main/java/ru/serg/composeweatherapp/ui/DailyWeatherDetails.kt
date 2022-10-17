package ru.serg.composeweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.ui.theme.descriptionSubHeader
import ru.serg.composeweatherapp.ui.theme.headerModifier
import ru.serg.composeweatherapp.ui.theme.headerStyle
import ru.serg.composeweatherapp.utils.Ext
import ru.serg.composeweatherapp.utils.Ext.firstLetterToUpperCase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DailyWeatherDetails(daily: OneCallResponse.Daily, modifier: Modifier, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        )
    ) {

        Card(
//            modifier = Modifier.padding(horizontal = 32.dp, vertical = 130.dp)
//                .height(320.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(24.dp),
//            backgroundColor = Color.DarkGray,

//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color.LightGray)
//                .height(200.dp)
//                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
//                    .height(260.dp)
            ) {
                Text(
                    text = Ext.getFullDate(daily.dt),
                    style = MaterialTheme.typography.headerStyle,
                    modifier = Modifier
                        .headerModifier()
                )

                Text(text = daily.weather?.first()?.run { "${main}, ${description}" }?:"",
                    style = MaterialTheme.typography.descriptionSubHeader,
                    color = Color.Yellow,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )


            }

        }
    }
}

