package ru.serg.designsystem.top_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.common.ConnectionState
import ru.serg.common.currentConnectionsState
import ru.serg.common.observeConnectivityAsFlow
import ru.serg.designsystem.theme.customColors
import ru.serg.strings.R.string

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    AnimatedVisibility(
        visible = !isConnected,
        enter = expandVertically(
            animationSpec = tween(300)
        ) + slideInVertically(
            animationSpec = tween(500),
            initialOffsetY = { -it }
        ),
        exit = shrinkVertically(
            animationSpec = tween(500, delayMillis = 3000)
        )
    ) {
        val backgroundColor by animateColorAsState(
            if (isConnected) MaterialTheme.customColors.green else MaterialTheme.customColors.red,
            animationSpec = tween(700, easing = FastOutSlowInEasing), label = ""
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                )
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isConnected) Icons.Rounded.CloudDownload else Icons.Rounded.CloudOff,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(id = if (isConnected) string.connection_restored else string.no_connection),
                modifier = Modifier
                    .padding(8.dp),
                color = Color.White
            )

        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState(initialValue = context.currentConnectionsState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}

@Preview
@Composable
fun PreviewConnectionState() {
    ConnectivityStatus()
}