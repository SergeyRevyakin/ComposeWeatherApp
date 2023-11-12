package ru.serg.widget_settings_feature.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.serg.designsystem.simple_items.PrimaryButton
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.strings.R.string
import ru.serg.widget_settings_feature.items.ColorBoxItem
import ru.serg.widget_settings_feature.utils.fillColorSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialogScreen(
    onDismissRequest: () -> Unit,
    color: Color,
    onColor: (Color) -> Unit,
) {


    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(),
        modifier = Modifier.fillMaxWidth()
    )
    {

        val context = LocalContext.current
        val colorState = remember {
            mutableStateOf(
                color
            )
        }

        val colorSet = fillColorSet(context = context)

        val selectedElementIndex = remember {
            mutableIntStateOf(
                colorSet.indexOf(color)
            )
        }

//        val textValue = remember {
//            mutableStateOf(
//                ""
//            )
//        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    MaterialTheme.colors.surface
                        .copy(alpha = 0.9f)
                        .compositeOver(MaterialTheme.colors.onBackground),
                    RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(
                        animateColorAsState(
                            targetValue = colorState.value,
                            label = ""
                        ).value,
                        RoundedCornerShape(16.dp)
                    )
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(48.dp),
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(colorSet.size) {
                    ColorBoxItem(
                        colorC = colorSet.elementAt(it),
                        isSelected = selectedElementIndex.intValue == it,
                        modifier = Modifier.aspectRatio(1f)
                    ) { color ->
                        selectedElementIndex.intValue = it
                        colorState.value = color
                    }
                }
            }
//
//            Text(
//                text = "Or add your own color",
//                color = MaterialTheme.colors.onBackground,
//                modifier = Modifier.fillMaxWidth(),
//                fontSize = 20.sp
//            )

//            OutlinedTextField(
//                value = textValue.value,
//                onValueChange = {
//                    colorState.value = parseColor(it)
//                    textValue.value = it
//                },
//                label = {
//                    Text(text = stringResource(id = string.enter_color_code))
//                },
//                placeholder = {
//                    Text(
//                        text = stringResource(id = string.color_code_format),
//                        fontSize = 18.sp
//                    )
//                },
//                trailingIcon = {
//                    Icon(
//                        Icons.Filled.Close,
//                        contentDescription = stringResource(id = string.accessibility_desc_clear_field_icon),
//                        modifier = Modifier
//                            .clickable {
////                                onValueChange(Constants.EMPTY_STRING)
//                            }
//                            .clip(CircleShape)
//                    )
//                },
//                modifier = Modifier
//                    .padding(vertical = 24.dp),
//                singleLine = true,
//                shape = RoundedCornerShape(12.dp),
//                textStyle = TextStyle(fontSize = 18.sp)
//            )

            PrimaryButton(
                headerText = stringResource(id = string.pick_color),
            ) {
                onColor(colorState.value)
            }
        }
    }
}

fun parseColor(string: String): Color {
    return Color(string.toULong())
}

@Preview(showBackground = true)
@Composable
private fun PreviewColorPicker() {
    ComposeWeatherAppTheme {
        ColorPickerDialogScreen(
            {}, Color.Blue, {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewColorPickerDark() {
    val isDark = remember {
        mutableStateOf(true)
    }
    ComposeWeatherAppTheme(isDark) {
        ColorPickerDialogScreen(
            {}, Color.Red, { },
        )
    }
}