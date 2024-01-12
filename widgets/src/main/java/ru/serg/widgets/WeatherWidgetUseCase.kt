package ru.serg.widgets

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
    ) { color, bigFontSize, smallFontSize, bottomPadding ->
        WidgetDataSettings(color.toULong(), bigFontSize, smallFontSize, bottomPadding)
    }

}
