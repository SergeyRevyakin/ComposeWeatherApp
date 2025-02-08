package ru.serg.settings_feature.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.serg.designsystem.theme.ComposeWeatherAppTheme
import ru.serg.designsystem.theme.settingsSubText
import ru.serg.model.enums.Units
import ru.serg.strings.R.string

@Composable
fun RadioButtonGroup(
    header: String,
    nameList: List<String>,
    descriptionList: List<String>,
    selectedPosition: MutableState<Int>,
    modifier: Modifier = Modifier,
    onSelectListener: (Int) -> Unit,
) {
    Column(
        modifier = modifier
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
        mutableIntStateOf(0)
    }
    ComposeWeatherAppTheme {

        RadioButtonGroup(
            header = stringResource(id = string.measurement_units),
            nameList = Units.entries.map { stringResource(id = it.title) },
            descriptionList = Units.entries.map { stringResource(id = it.description) },
            selectedPosition = position,
            onSelectListener = { }
        )
    }
}