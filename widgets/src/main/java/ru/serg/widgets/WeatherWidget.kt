package ru.serg.widgets

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.Color
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
import ru.serg.datastore.DataStoreDataSource
import ru.serg.local.LocalDataSource
import ru.serg.widgets.ui.MainWeatherWidget
import ru.serg.widgets.worker.UpdateWorker

class WeatherWidget : GlanceAppWidget() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ExampleContentProviderEntryPoint {
        fun localDataSource(): LocalDataSource
        fun dataStoreDataSource(): DataStoreDataSource
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val appContext = context.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                ExampleContentProviderEntryPoint::class.java
            )

        val localDataSource = hiltEntryPoint.localDataSource()
        val dataStoreDataSource = hiltEntryPoint.dataStoreDataSource()

        UpdateWorker.setupPeriodicWork(appContext)

        localDataSource.getFavouriteCityWeather().collectLatest { weatherItem ->
            Log.e("WeatherWidget", "On collectedLatest")
            dataStoreDataSource.widgetColorCode.collectLatest {
                val color = Color(it.toULong())
                provideContent {
                    GlanceTheme(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                            GlanceTheme.colors
                        else
                            NonMaterialGlanceTheme.colors
                    )
                    {
                        MainWeatherWidget(
                            weatherItem.hourlyWeatherList.first(),
                            weatherItem.cityItem,
                            color
                        )
                    }
                }
            }
        }
    }
}

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()
}

