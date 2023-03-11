package ru.serg.composeweatherapp.ui.elements.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.composeweatherapp.ui.theme.settingsSubText
import ru.serg.composeweatherapp.utils.Constants

@Composable
fun RadioButtonGroup(
    header: String,
    nameList: List<String>,
    descriptionList: List<String>,
    selectedPosition: MutableState<Int>,
    onSelectListener: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = header,
            fontSize = 20.sp
        )
        nameList.forEachIndexed { index, s ->
            Row(
                modifier = Modifier
                    .clickable {
                        onSelectListener(index)
                    }
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = s,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = descriptionList[index],
                        style = settingsSubText
                    )

                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterVertically),
                ) {
                    RadioButton(
                        selected = index == selectedPosition.value,
                        onClick = { onSelectListener(index) },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewRadioButtonGroup() {
    val position = remember {
        mutableStateOf(0)
    }

    RadioButtonGroup(
        header = "Units of measurement",
        nameList = Constants.DataStore.Units.values().map { it.title },
        descriptionList = Constants.DataStore.Units.values().map { it.description },
        selectedPosition = position,
        onSelectListener = { }
    )
}