package com.serg.network_self_proxy

import com.serg.network_self_proxy.ProxyNetworkModule.Companion.CITY_SEARCH
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.FORECAST
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.LANG
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.LAT
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.LON
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.NAME
import com.serg.network_self_proxy.ProxyNetworkModule.Companion.UNITS
import com.serg.network_self_proxy.dto.CityModel
import com.serg.network_self_proxy.dto.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.enums.Units
import java.util.Locale
import javax.inject.Inject

class SelfProxyRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val dataStoreDataSource: DataStoreDataSource
) : SelfProxyRemoteDataSource {

    override fun getSelfProxyForecast(lat: Double, lon: Double): Flow<WeatherResponse> =
        flow {
            val units = Units.entries[dataStoreDataSource.measurementUnits.first()].parameterCode
            emit(
                httpClient.get {
                    url {
                        appendPathSegments(FORECAST)
                        parameter(LON, lon)
                        parameter(LAT, lat)
                        parameter(LANG, Locale.getDefault().language)
                        parameter(UNITS, units)
                    }
                }.body()
            )
        }

    override fun getCitiesForSearch(input: String?): Flow<List<CityModel>> =
        flow {
            emit(
                httpClient.get {
                    url {
                        appendPathSegments(CITY_SEARCH)
                        parameter(NAME, input)
                        parameter(LANG, Locale.getDefault().language)
                    }
                }.body()
            )
        }

}