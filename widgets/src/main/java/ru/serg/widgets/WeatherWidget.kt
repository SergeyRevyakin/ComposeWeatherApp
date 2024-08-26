package ru.serg.widgets

import android.content.Context
import android.os.Build
import android.util.Log
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

class WeatherWidget : GlanceAppWidget() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WidgetDependenciesProvider {
        fun localDataSource(): LocalDataSource
        fun dataStoreDataSource(): DataStoreDataSource
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val widgetDependencies =
            EntryPointAccessors.fromApplication(
                appContext,
                WidgetDependenciesProvider::class.java
            )

        val localDataSource = widgetDependencies.localDataSource()
        val dataStoreDataSource = widgetDependencies.dataStoreDataSource()

        val weatherWidgetUseCase = WeatherWidgetUseCase(dataStoreDataSource)

        localDataSource.getFavouriteCityWeather().collectLatest { weatherItem ->
            weatherWidgetUseCase.prepareData().collectLatest { settings ->
                Log.e("TAG", "Collect latest")

                provideContent {
                    GlanceTheme(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                            GlanceTheme.colors
                        else
                            NonMaterialGlanceTheme.colors
                    )
                    {
                        MainWeatherWidget(
                            weatherItem.hourlyWeatherList,
                            weatherItem.cityItem,
                            settings,
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

