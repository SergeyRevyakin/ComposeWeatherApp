package ru.serg.choose_city_feature.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.choose_city_feature.Constants
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.strings.R.string

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: ((String) -> Unit) = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = stringResource(id = string.enter_city_name))
        },
        placeholder = {
            Text(
                text = stringResource(id = string.eg_city_name),
                fontSize = 18.sp
            )
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(id = string.accessibility_desc_clear_field_icon),
                modifier = Modifier
                    .clickable {
                        onValueChange(Constants.EMPTY_STRING)
                    }
                    .clip(CircleShape)
            )
        },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(fontSize = 18.sp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewSearchTextField() {
    val isDarkTheme = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDarkTheme) {
        SearchTextField(
            value = "Moscow"
        )
    }
}