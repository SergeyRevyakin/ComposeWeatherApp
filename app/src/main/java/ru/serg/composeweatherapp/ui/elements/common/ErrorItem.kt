package ru.serg.composeweatherapp.ui.elements.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorItem(
    errorText: String?,
    stringResource: Int? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(24.dp)
            ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Text(
                    text = "We can't show you weather because:",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SentimentDissatisfied,
                        contentDescription = null,
                        Modifier
                            .padding(8.dp)
                            .size(64.dp),
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = errorText ?: "",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewErrorItem() {
    ErrorItem(errorText = "No connection!", null)
}