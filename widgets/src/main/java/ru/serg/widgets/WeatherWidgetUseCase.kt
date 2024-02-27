package ru.serg.widgets

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.serg.datastore.Constants
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.WidgetDataSettings

class WeatherWidgetUseCase(
    private val dataStoreDataSource: DataStoreDataSource
) {
    fun prepareData(): Flow<WidgetDataSettings> = combine(
        dataStoreDataSource.widgetColorCode,
        dataStoreDataSource.widgetBigFontSize,
        dataStoreDataSource.widgetSmallFontSize,
        dataStoreDataSource.widgetBottomPadding,
        dataStoreDataSource.isWidgetSystemDataShown,
        dataStoreDataSource.widgetIconSize
    ) { dataArray ->
        try {
            val color = dataArray[0] as Long
            val bigFontSize = dataArray[1] as Int
            val smallFontSize = dataArray[2] as Int
            val bottomPadding = dataArray[3] as Int
            val isSystemDataShown = dataArray[4] as Boolean
            val iconSize = dataArray[5] as Int

            WidgetDataSettings(
                color = color.toULong(),
                bigFontSize = bigFontSize,
                smallFontSize = smallFontSize,
                bottomPadding = bottomPadding,
                iconSize = iconSize,
                isSystemDataShown = isSystemDataShown
            )
        } catch (_: Exception) {
            WidgetDataSettings(
                color = Constants.WidgetSettings.WHITE_COLOR_CODE.toULong(),
                bigFontSize = Constants.WidgetSettings.DEFAULT_BIG_FONT_SIZE,
                smallFontSize = Constants.WidgetSettings.DEFAULT_SMALL_FONT_SIZE,
                bottomPadding = Constants.WidgetSettings.DEFAULT_BOTTOM_PADDING,
                iconSize = Constants.WidgetSettings.DEFAULT_WIDGET_ICON_SIZE,
                isSystemDataShown = Constants.WidgetSettings.DEFAULT_IS_WIDGET_SYSTEM_DATA_SHOWN
            )
        }
    }

}
