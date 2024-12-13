package ru.serg.weather_elements.bottom_sheets

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DialogContainer(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.statusBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface
            .copy(alpha = 0.9f)
            .compositeOver(MaterialTheme.colorScheme.onSurface),
    ) {
        content()
        Spacer(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBarsIgnoringVisibility
            )
        )
    }
}