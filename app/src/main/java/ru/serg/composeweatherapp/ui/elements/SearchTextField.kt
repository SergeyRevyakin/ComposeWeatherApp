package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.utils.Constants

@Composable
fun SearchTextField(
    value: String,
    onValueChange: ((String) -> Unit),
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = "Moscow",
                fontSize = 18.sp
            )
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Close",
                modifier = Modifier.clickable {
                    onValueChange(Constants.EMPTY_STRING)
                })
        },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(fontSize = 18.sp)
    )
}