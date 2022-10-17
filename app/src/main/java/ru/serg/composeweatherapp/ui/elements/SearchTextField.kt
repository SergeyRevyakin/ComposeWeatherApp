package ru.serg.composeweatherapp.ui.elements

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.utils.Constants

@Composable
fun SearchTextField(
    onValueChange: ((String) -> Unit),
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf(TextFieldValue(Constants.EMPTY_STRING))
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it.text)
        },
        placeholder = {
            Text(
                text = "Moscow",
                fontSize = 18.sp
            )
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(fontSize = 18.sp)
    )
}