package ru.serg.widgets

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.collectLatest
import ru.serg.local.LocalDataSource
import ru.serg.widgets.ui.MainWeatherWidget
import ru.serg.widgets.worker.UpdateWorker

class WeatherWidget : GlanceAppWidget() {
    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface ExampleContentProviderEntryPoint {
        fun localDataSource(): LocalDataSource
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val appContext = context.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                ExampleContentProviderEntryPoint::class.java
            )

        val localDataSource = hiltEntryPoint.localDataSource()

        UpdateWorker.setupPeriodicWork(appContext)

        localDataSource.getFavouriteCityWeather().collectLatest {
            Log.e("WeatherWidget", "On collectedLatest")
            provideContent {
                GlanceTheme(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        GlanceTheme.colors
                    else
                        NonMaterialGlanceTheme.colors
                ) {
                    MainWeatherWidget(
                        it.hourlyWeatherList.first(),
                        it.cityItem
                    )
                }
            }
        }
    }
}

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()
}

