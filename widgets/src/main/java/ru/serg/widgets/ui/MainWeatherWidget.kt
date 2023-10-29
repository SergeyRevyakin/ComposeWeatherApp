package ru.serg.widgets.ui

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import ru.serg.model.CityItem
import ru.serg.model.HourlyWeather
import ru.serg.service.FetchWeatherService
import ru.serg.widgets.Constants
import ru.serg.widgets.R
import ru.serg.widgets.Utils
import ru.serg.widgets.getHour
import kotlin.math.roundToInt

@Composable
fun MainWeatherWidget(
    hourWeather: HourlyWeather,
    cityItem: CityItem,
) {
    Log.e("ComposeWeatherWidget", "Composition started")
    val packageName = LocalContext.current.packageName
    val clockView = RemoteViews(packageName, R.layout.text_clock_layout)
    val ctx = LocalContext.current

    Row(
        modifier = GlanceModifier.fillMaxSize()
            .clickable {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    component = ComponentName(
                        packageName,
                        Constants.MAIN_ACTIVITY_PACKAGE
                    )
                    ctx.startActivity(this)
                }
            },
    ) {

        Column(
            modifier = GlanceModifier.defaultWeight()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.Start
        ) {
            Row(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                AndroidRemoteViews(
                    remoteViews = clockView,
                    containerViewId = View.NO_ID,
                    content = {
                        clockView.apply {
                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT24HOUR,
                                Utils.get24HoursFormat()
                            )

                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT12HOUR,
                                Utils.get12HoursFormat()
                            )

                            setTextColor(
                                R.id.c_clock,
                                GlanceTheme.colors.primary.getColor(LocalContext.current).toArgb()
                            )
                        }
                    }
                )
            }
            Row(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Text(
                    text = cityItem.name,
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = 18.sp
                    ),
                )
            }
        }

        Column(
            modifier = GlanceModifier.defaultWeight()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.CenterVertically,
            ) {
                Text(
                    text = hourWeather.currentTemp.roundToInt().toString() + "°",
                    style = TextStyle(
                        fontSize = 36.sp, fontWeight = FontWeight.Normal, color = ColorProvider(
                            GlanceTheme.colors.primary.getColor(LocalContext.current)
                        )
                    )
                )

                Image(
                    provider = ImageProvider(hourWeather.weatherIcon), contentDescription = "",
                    modifier = GlanceModifier.size(60.dp),
                    colorFilter = ColorFilter.tint(GlanceTheme.colors.primary)
                )
            }

            Row(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.padding(vertical = 6.dp)
            ) {
                Text(
                    text = hourWeather.weatherDescription,
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = 18.sp
                    )
                )
            }

            Row(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.clickable {
                    val intent = Intent(ctx, FetchWeatherService::class.java)
                    ctx.startForegroundService(intent)
                }
            ) {
                Text(
                    text = "Last updated " + getHour(cityItem.lastTimeUpdated),
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = 12.sp
                    )
                )
                Image(
                    provider = ImageProvider(ru.serg.drawables.R.drawable.ic_refresh),
                    contentDescription = "",
                    modifier = GlanceModifier.size(18.dp),
                    colorFilter = ColorFilter.tint(GlanceTheme.colors.primary)
                )
            }


            Row(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.CenterVertically,
            ) {
                Text(
                    text = "Last recomposition " + getHour(System.currentTimeMillis()),
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}
