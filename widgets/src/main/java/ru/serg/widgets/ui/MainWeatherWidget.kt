package ru.serg.widgets.ui

import android.content.ComponentName
import android.content.Intent
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
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
import ru.serg.model.WidgetDataSettings
import ru.serg.service.FetchWeatherService
import ru.serg.widgets.Constants
import ru.serg.widgets.R
import ru.serg.widgets.Utils
import ru.serg.widgets.badWeatherList
import ru.serg.widgets.getHour
import kotlin.math.roundToInt

@Composable
fun MainWeatherWidget(
    nextWeatherList: List<HourlyWeather>,
    cityItem: CityItem,
    settings: WidgetDataSettings,
) {
    Log.e("ComposeWeatherWidget", "Composition started")
    val packageName = LocalContext.current.packageName
    val clockView = RemoteViews(packageName, R.layout.text_clock_layout)
    val dateView = RemoteViews(packageName, R.layout.text_clock_layout)
    val ctx = LocalContext.current
    val currentHourWeather = nextWeatherList.first()

    val currentColor by remember {
        mutableStateOf(Color(settings.color))
    }

    val bigFont by remember {
        mutableIntStateOf(settings.bigFontSize)
    }
    val smallFont by remember {
        mutableIntStateOf(settings.smallFontSize)
    }
    val paddingBottom by remember {
        mutableStateOf(settings.bottomPadding.dp)
    }

    val isSystemDataShown by remember {
        mutableStateOf(settings.isSystemDataShown)
    }

    Column(
        modifier = GlanceModifier.fillMaxSize(),
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(bottom = paddingBottom),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.defaultWeight()
                    .clickable {
                        Intent().apply {
                            action = AlarmClock.ACTION_SET_ALARM
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            ctx.startActivity(this)
                        }
                    }
            ) {
                AndroidRemoteViews(
                    remoteViews = clockView,
                    containerViewId = View.NO_ID,
                    content = {
                        clockView.apply {
                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT24HOUR,
                                Utils.get24HoursFormat(bigFont)
                            )

                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT12HOUR,
                                Utils.get12HoursFormat(bigFont, smallFont)
                            )

                            setTextColor(
                                R.id.c_clock,
                                currentColor.toArgb()
                            )
                        }
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.CenterVertically,
                modifier = GlanceModifier.defaultWeight()
                    .clickable {
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            component = ComponentName(
                                packageName,
                                Constants.MAIN_ACTIVITY_PACKAGE
                            )
                            ctx.startActivity(this)
                        }
                    }
            ) {
                Row(
                    horizontalAlignment = Alignment.End,
                    verticalAlignment = Alignment.Vertical.CenterVertically,
                ) {
                    Text(
                        text = currentHourWeather.currentTemp.roundToInt().toString() + "°",
                        style = TextStyle(
                            fontSize = bigFont.sp,
                            fontWeight = FontWeight.Normal,
                            color = ColorProvider(
                                currentColor
                            )
                        )
                    )

                    Image(
                        provider = ImageProvider(currentHourWeather.weatherIcon),
                        contentDescription = "",
                        modifier = GlanceModifier.size(settings.iconSize.dp),
                        colorFilter = ColorFilter.tint(ColorProvider(currentColor))
                    )
                }
            }
        }
        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(bottom = paddingBottom),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.Vertical.Top,
                modifier = GlanceModifier.defaultWeight()
                    .clickable {
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            setData(
                                CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
                                    .build()
                            )
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            ctx.startActivity(this)
                        }
                    }
            ) {
                AndroidRemoteViews(
                    remoteViews = dateView,
                    containerViewId = View.NO_ID,
                    content = {
                        dateView.apply {
                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT24HOUR,
                                Utils.getDate(smallFont)
                            )

                            setCharSequence(
                                R.id.c_clock,
                                Constants.SET_FORMAT12HOUR,
                                Utils.getDate(smallFont)
                            )

                            setTextColor(
                                R.id.c_clock,
                                currentColor.toArgb()
                            )
                        }
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Vertical.Top,
                modifier = GlanceModifier.defaultWeight()
                    .clickable {
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            component = ComponentName(
                                packageName,
                                Constants.MAIN_ACTIVITY_PACKAGE
                            )
                            ctx.startActivity(this)
                        }
                    }
            ) {
                Row(
                    horizontalAlignment = Alignment.End,
                    verticalAlignment = Alignment.Vertical.Top,
                ) {
                    Text(
                        text = currentHourWeather.weatherDescription,
                        style = TextStyle(
                            color = ColorProvider(currentColor),
                            fontSize = smallFont.sp
                        )
                    )
                }
            }
        }

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            val nextHourWeather = nextWeatherList.getOrNull(1)
            val isBadWeatherExpected = badWeatherList().contains(nextHourWeather?.weatherIcon)

            if (isBadWeatherExpected && nextHourWeather != null) {
                Text(
                    text = LocalContext.current.getString(
                        ru.serg.strings.R.string.widget_weather_changes_expected,
                        nextHourWeather.weatherDescription
                    ),
                    style = TextStyle(
                        color = ColorProvider(currentColor),
                        fontSize = smallFont.sp
                    ),
                )
            }
        }

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.Vertical.Top,
                modifier = GlanceModifier.defaultWeight()
            ) {
                Text(
                    text = cityItem.name,
                    style = TextStyle(
                        color = ColorProvider(currentColor),
                        fontSize = smallFont.sp
                    ),
                )
            }

            if (isSystemDataShown) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalAlignment = Alignment.Vertical.Top,
                    modifier = GlanceModifier.defaultWeight()
                ) {
                    Row(
                        horizontalAlignment = Alignment.End,
                        verticalAlignment = Alignment.Vertical.CenterVertically,
                        modifier = GlanceModifier.clickable {
                            val intent = Intent(ctx, FetchWeatherService::class.java)
                            ctx.startForegroundService(intent)
                        }
                    ) {
                        Text(
                            text = LocalContext.current.getString(
                                ru.serg.strings.R.string.widget_last_updated,
                                getHour(cityItem.lastTimeUpdated)
                            ),
                            style = TextStyle(
                                color = ColorProvider(currentColor),
                                fontSize = 12.sp
                            )
                        )
                        Image(
                            provider = ImageProvider(ru.serg.drawables.R.drawable.ic_refresh),
                            contentDescription = "",
                            modifier = GlanceModifier.size(18.dp),
                            colorFilter = ColorFilter.tint(ColorProvider(currentColor))
                        )
                    }


                    Row(
                        horizontalAlignment = Alignment.End,
                        verticalAlignment = Alignment.Vertical.CenterVertically,
                    ) {
                        Text(
                            text = LocalContext.current.getString(
                                ru.serg.strings.R.string.widget_last_recomposition,
                                getHour(System.currentTimeMillis())
                            ),
                            style = TextStyle(
                                color = ColorProvider(currentColor),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}


