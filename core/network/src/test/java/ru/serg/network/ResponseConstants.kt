package ru.serg.network

object ResponseConstants {
    const val WEATHER_RESPONSE = """
        {"coord":{"lon":-122.084,"lat":37.422},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":9.8,"feels_like":8.91,"temp_min":6.99,"temp_max":13.97,"pressure":1021,"humidity":52},"visibility":10000,"wind":{"speed":2.06,"deg":260},"clouds":{"all":0},"dt":1669466649,"sys":{"type":1,"id":5122,"country":"US","sunrise":1669474770,"sunset":1669510336},"timezone":-28800,"id":5375480,"name":"Mountain View","cod":200}
    """

    const val ONECALL_RESPONSE = """
        {
    "lat": 37.422,
    "lon": -122.084,
    "timezone": "America/Los_Angeles",
    "timezone_offset": -28800,
    "current": {
        "dt": 1669466910,
        "sunrise": 1669474770,
        "sunset": 1669510336,
        "temp": 9.86,
        "feels_like": 8.84,
        "pressure": 1021,
        "humidity": 53,
        "dew_point": 0.74,
        "uvi": 0,
        "clouds": 0,
        "visibility": 10000,
        "wind_speed": 2.24,
        "wind_deg": 146,
        "wind_gust": 2.68,
        "weather": [
            {
                "id": 800,
                "main": "Clear",
                "description": "clear sky",
                "icon": "01n"
            }
        ]
    },
    "hourly": [
        {
            "dt": 1669464000,
            "temp": 10.19,
            "feels_like": 8.57,
            "pressure": 1021,
            "humidity": 50,
            "dew_point": 0.24,
            "uvi": 0,
            "clouds": 3,
            "visibility": 10000,
            "wind_speed": 0.24,
            "wind_deg": 24,
            "wind_gust": 0.73,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669467600,
            "temp": 9.86,
            "feels_like": 9.86,
            "pressure": 1021,
            "humidity": 53,
            "dew_point": 0.74,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.29,
            "wind_deg": 141,
            "wind_gust": 0.54,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669471200,
            "temp": 10.13,
            "feels_like": 8.5,
            "pressure": 1021,
            "humidity": 50,
            "dew_point": 0.18,
            "uvi": 0,
            "clouds": 17,
            "visibility": 10000,
            "wind_speed": 0.29,
            "wind_deg": 144,
            "wind_gust": 0.59,
            "weather": [
                {
                    "id": 801,
                    "main": "Clouds",
                    "description": "few clouds",
                    "icon": "02n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669474800,
            "temp": 10.47,
            "feels_like": 8.83,
            "pressure": 1021,
            "humidity": 48,
            "dew_point": -0.06,
            "uvi": 0,
            "clouds": 36,
            "visibility": 10000,
            "wind_speed": 0.59,
            "wind_deg": 270,
            "wind_gust": 0.74,
            "weather": [
                {
                    "id": 802,
                    "main": "Clouds",
                    "description": "scattered clouds",
                    "icon": "03d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669478400,
            "temp": 11.19,
            "feels_like": 9.59,
            "pressure": 1022,
            "humidity": 47,
            "dew_point": 0.31,
            "uvi": 0.17,
            "clouds": 55,
            "visibility": 10000,
            "wind_speed": 0.06,
            "wind_deg": 216,
            "wind_gust": 0.7,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669482000,
            "temp": 13.05,
            "feels_like": 11.56,
            "pressure": 1022,
            "humidity": 44,
            "dew_point": 1.09,
            "uvi": 0.65,
            "clouds": 69,
            "visibility": 10000,
            "wind_speed": 0.46,
            "wind_deg": 340,
            "wind_gust": 0.79,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669485600,
            "temp": 15.55,
            "feels_like": 14.23,
            "pressure": 1022,
            "humidity": 41,
            "dew_point": 1.39,
            "uvi": 1.43,
            "clouds": 73,
            "visibility": 10000,
            "wind_speed": 0.76,
            "wind_deg": 360,
            "wind_gust": 1.05,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669489200,
            "temp": 16.95,
            "feels_like": 15.69,
            "pressure": 1022,
            "humidity": 38,
            "dew_point": 1.73,
            "uvi": 2.16,
            "clouds": 5,
            "visibility": 10000,
            "wind_speed": 0.86,
            "wind_deg": 346,
            "wind_gust": 1.17,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669492800,
            "temp": 17.84,
            "feels_like": 16.62,
            "pressure": 1021,
            "humidity": 36,
            "dew_point": 1.76,
            "uvi": 2.52,
            "clouds": 2,
            "visibility": 10000,
            "wind_speed": 1.83,
            "wind_deg": 331,
            "wind_gust": 2.21,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669496400,
            "temp": 18.44,
            "feels_like": 17.25,
            "pressure": 1020,
            "humidity": 35,
            "dew_point": 1.59,
            "uvi": 2.32,
            "clouds": 2,
            "visibility": 10000,
            "wind_speed": 1.62,
            "wind_deg": 329,
            "wind_gust": 2.04,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669500000,
            "temp": 18.57,
            "feels_like": 17.4,
            "pressure": 1019,
            "humidity": 35,
            "dew_point": 1.61,
            "uvi": 1.68,
            "clouds": 1,
            "visibility": 10000,
            "wind_speed": 2.28,
            "wind_deg": 316,
            "wind_gust": 2.59,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669503600,
            "temp": 18.16,
            "feels_like": 17,
            "pressure": 1019,
            "humidity": 37,
            "dew_point": 2.29,
            "uvi": 0.89,
            "clouds": 1,
            "visibility": 10000,
            "wind_speed": 2.82,
            "wind_deg": 319,
            "wind_gust": 3.54,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669507200,
            "temp": 16.78,
            "feels_like": 15.61,
            "pressure": 1019,
            "humidity": 42,
            "dew_point": 2.98,
            "uvi": 0.27,
            "clouds": 1,
            "visibility": 10000,
            "wind_speed": 2.45,
            "wind_deg": 311,
            "wind_gust": 3.59,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669510800,
            "temp": 14.71,
            "feels_like": 13.46,
            "pressure": 1019,
            "humidity": 47,
            "dew_point": 2.57,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 2.05,
            "wind_deg": 304,
            "wind_gust": 3.15,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669514400,
            "temp": 13.97,
            "feels_like": 12.68,
            "pressure": 1019,
            "humidity": 48,
            "dew_point": 2.29,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.9,
            "wind_deg": 294,
            "wind_gust": 1.22,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669518000,
            "temp": 13.6,
            "feels_like": 12.27,
            "pressure": 1019,
            "humidity": 48,
            "dew_point": 2.18,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 1.17,
            "wind_deg": 334,
            "wind_gust": 1.48,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669521600,
            "temp": 13.12,
            "feels_like": 11.82,
            "pressure": 1020,
            "humidity": 51,
            "dew_point": 2.47,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 1.07,
            "wind_deg": 323,
            "wind_gust": 1.37,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669525200,
            "temp": 12.71,
            "feels_like": 11.45,
            "pressure": 1020,
            "humidity": 54,
            "dew_point": 2.98,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.98,
            "wind_deg": 325,
            "wind_gust": 1.28,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669528800,
            "temp": 12.38,
            "feels_like": 11.16,
            "pressure": 1020,
            "humidity": 57,
            "dew_point": 3.36,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.54,
            "wind_deg": 336,
            "wind_gust": 0.82,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669532400,
            "temp": 12.12,
            "feels_like": 10.9,
            "pressure": 1020,
            "humidity": 58,
            "dew_point": 3.38,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.61,
            "wind_deg": 50,
            "wind_gust": 0.7,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669536000,
            "temp": 11.9,
            "feels_like": 10.66,
            "pressure": 1020,
            "humidity": 58,
            "dew_point": 3.08,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.3,
            "wind_deg": 123,
            "wind_gust": 0.68,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669539600,
            "temp": 11.72,
            "feels_like": 10.44,
            "pressure": 1019,
            "humidity": 57,
            "dew_point": 2.81,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.43,
            "wind_deg": 119,
            "wind_gust": 0.68,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669543200,
            "temp": 11.54,
            "feels_like": 10.24,
            "pressure": 1019,
            "humidity": 57,
            "dew_point": 2.6,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.16,
            "wind_deg": 125,
            "wind_gust": 0.46,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669546800,
            "temp": 11.34,
            "feels_like": 10.02,
            "pressure": 1019,
            "humidity": 57,
            "dew_point": 2.51,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.5,
            "wind_deg": 70,
            "wind_gust": 0.59,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669550400,
            "temp": 11.12,
            "feels_like": 9.8,
            "pressure": 1019,
            "humidity": 58,
            "dew_point": 2.43,
            "uvi": 0,
            "clouds": 0,
            "visibility": 10000,
            "wind_speed": 0.15,
            "wind_deg": 84,
            "wind_gust": 0.41,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669554000,
            "temp": 10.93,
            "feels_like": 9.59,
            "pressure": 1019,
            "humidity": 58,
            "dew_point": 2.27,
            "uvi": 0,
            "clouds": 5,
            "visibility": 10000,
            "wind_speed": 0.68,
            "wind_deg": 114,
            "wind_gust": 0.85,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669557600,
            "temp": 10.78,
            "feels_like": 9.4,
            "pressure": 1019,
            "humidity": 57,
            "dew_point": 1.93,
            "uvi": 0,
            "clouds": 7,
            "visibility": 10000,
            "wind_speed": 1.15,
            "wind_deg": 104,
            "wind_gust": 1.21,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669561200,
            "temp": 10.61,
            "feels_like": 9.19,
            "pressure": 1019,
            "humidity": 56,
            "dew_point": 1.61,
            "uvi": 0,
            "clouds": 12,
            "visibility": 10000,
            "wind_speed": 0.93,
            "wind_deg": 124,
            "wind_gust": 1.13,
            "weather": [
                {
                    "id": 801,
                    "main": "Clouds",
                    "description": "few clouds",
                    "icon": "02n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669564800,
            "temp": 11.65,
            "feels_like": 10.25,
            "pressure": 1019,
            "humidity": 53,
            "dew_point": 1.68,
            "uvi": 0.16,
            "clouds": 15,
            "visibility": 10000,
            "wind_speed": 1.11,
            "wind_deg": 130,
            "wind_gust": 1.2,
            "weather": [
                {
                    "id": 801,
                    "main": "Clouds",
                    "description": "few clouds",
                    "icon": "02d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669568400,
            "temp": 13.76,
            "feels_like": 12.39,
            "pressure": 1019,
            "humidity": 46,
            "dew_point": 1.76,
            "uvi": 0.62,
            "clouds": 30,
            "visibility": 10000,
            "wind_speed": 0.58,
            "wind_deg": 155,
            "wind_gust": 0.84,
            "weather": [
                {
                    "id": 802,
                    "main": "Clouds",
                    "description": "scattered clouds",
                    "icon": "03d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669572000,
            "temp": 15.56,
            "feels_like": 14.24,
            "pressure": 1019,
            "humidity": 41,
            "dew_point": 1.73,
            "uvi": 1.36,
            "clouds": 41,
            "visibility": 10000,
            "wind_speed": 0.18,
            "wind_deg": 220,
            "wind_gust": 0.54,
            "weather": [
                {
                    "id": 802,
                    "main": "Clouds",
                    "description": "scattered clouds",
                    "icon": "03d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669575600,
            "temp": 17.02,
            "feels_like": 15.77,
            "pressure": 1019,
            "humidity": 38,
            "dew_point": 1.69,
            "uvi": 2.06,
            "clouds": 64,
            "visibility": 10000,
            "wind_speed": 0.6,
            "wind_deg": 307,
            "wind_gust": 0.79,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669579200,
            "temp": 17.71,
            "feels_like": 16.48,
            "pressure": 1018,
            "humidity": 36,
            "dew_point": 1.65,
            "uvi": 2.41,
            "clouds": 74,
            "visibility": 10000,
            "wind_speed": 1.22,
            "wind_deg": 300,
            "wind_gust": 1.52,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669582800,
            "temp": 17.95,
            "feels_like": 16.77,
            "pressure": 1017,
            "humidity": 37,
            "dew_point": 1.92,
            "uvi": 2.22,
            "clouds": 83,
            "visibility": 10000,
            "wind_speed": 1.43,
            "wind_deg": 308,
            "wind_gust": 1.71,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669586400,
            "temp": 18.17,
            "feels_like": 17.01,
            "pressure": 1017,
            "humidity": 37,
            "dew_point": 2.41,
            "uvi": 1.56,
            "clouds": 86,
            "visibility": 10000,
            "wind_speed": 1.02,
            "wind_deg": 298,
            "wind_gust": 1.18,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669590000,
            "temp": 17.66,
            "feels_like": 16.55,
            "pressure": 1016,
            "humidity": 41,
            "dew_point": 3.17,
            "uvi": 0.83,
            "clouds": 89,
            "visibility": 10000,
            "wind_speed": 1.51,
            "wind_deg": 309,
            "wind_gust": 1.54,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669593600,
            "temp": 16.17,
            "feels_like": 15.15,
            "pressure": 1016,
            "humidity": 50,
            "dew_point": 4.59,
            "uvi": 0.25,
            "clouds": 91,
            "visibility": 10000,
            "wind_speed": 1.86,
            "wind_deg": 309,
            "wind_gust": 2.4,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04d"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669597200,
            "temp": 14.05,
            "feels_like": 13.16,
            "pressure": 1016,
            "humidity": 63,
            "dew_point": 6.02,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 2.16,
            "wind_deg": 299,
            "wind_gust": 3.29,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669600800,
            "temp": 12.99,
            "feels_like": 12.25,
            "pressure": 1016,
            "humidity": 73,
            "dew_point": 7.48,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.87,
            "wind_deg": 288,
            "wind_gust": 3.07,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669604400,
            "temp": 12.38,
            "feels_like": 11.68,
            "pressure": 1017,
            "humidity": 77,
            "dew_point": 7.65,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.43,
            "wind_deg": 269,
            "wind_gust": 2.38,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669608000,
            "temp": 11.9,
            "feels_like": 11.18,
            "pressure": 1017,
            "humidity": 78,
            "dew_point": 7.44,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.54,
            "wind_deg": 262,
            "wind_gust": 2.49,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669611600,
            "temp": 11.54,
            "feels_like": 10.81,
            "pressure": 1016,
            "humidity": 79,
            "dew_point": 7.23,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.44,
            "wind_deg": 237,
            "wind_gust": 2.27,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669615200,
            "temp": 11.32,
            "feels_like": 10.57,
            "pressure": 1016,
            "humidity": 79,
            "dew_point": 7.08,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.43,
            "wind_deg": 227,
            "wind_gust": 2.31,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669618800,
            "temp": 11.09,
            "feels_like": 10.34,
            "pressure": 1016,
            "humidity": 80,
            "dew_point": 7,
            "uvi": 0,
            "clouds": 100,
            "visibility": 10000,
            "wind_speed": 1.57,
            "wind_deg": 228,
            "wind_gust": 2.75,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669622400,
            "temp": 10.91,
            "feels_like": 10.17,
            "pressure": 1015,
            "humidity": 81,
            "dew_point": 7.06,
            "uvi": 0,
            "clouds": 92,
            "visibility": 10000,
            "wind_speed": 1.42,
            "wind_deg": 238,
            "wind_gust": 2.79,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669626000,
            "temp": 10.7,
            "feels_like": 10.02,
            "pressure": 1015,
            "humidity": 84,
            "dew_point": 7.31,
            "uvi": 0,
            "clouds": 78,
            "visibility": 10000,
            "wind_speed": 1.28,
            "wind_deg": 246,
            "wind_gust": 2.55,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669629600,
            "temp": 10.53,
            "feels_like": 9.88,
            "pressure": 1015,
            "humidity": 86,
            "dew_point": 7.54,
            "uvi": 0,
            "clouds": 69,
            "visibility": 10000,
            "wind_speed": 1.33,
            "wind_deg": 248,
            "wind_gust": 3.01,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        },
        {
            "dt": 1669633200,
            "temp": 10.48,
            "feels_like": 9.86,
            "pressure": 1015,
            "humidity": 87,
            "dew_point": 7.7,
            "uvi": 0,
            "clouds": 66,
            "visibility": 10000,
            "wind_speed": 1.63,
            "wind_deg": 266,
            "wind_gust": 4.04,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04n"
                }
            ],
            "pop": 0
        }
    ],
    "daily": [
        {
            "dt": 1669489200,
            "sunrise": 1669474770,
            "sunset": 1669510336,
            "moonrise": 1669486680,
            "moonset": 1669520160,
            "moon_phase": 0.11,
            "temp": {
                "day": 16.95,
                "min": 9.86,
                "max": 18.57,
                "night": 12.12,
                "eve": 14.71,
                "morn": 9.86
            },
            "feels_like": {
                "day": 15.69,
                "night": 10.9,
                "eve": 13.46,
                "morn": 9.86
            },
            "pressure": 1022,
            "humidity": 38,
            "dew_point": 1.73,
            "wind_speed": 2.82,
            "wind_deg": 319,
            "wind_gust": 3.59,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "clouds": 5,
            "pop": 0,
            "uvi": 2.52
        },
        {
            "dt": 1669575600,
            "sunrise": 1669561230,
            "sunset": 1669596716,
            "moonrise": 1669576440,
            "moonset": 1669610940,
            "moon_phase": 0.15,
            "temp": {
                "day": 17.02,
                "min": 10.61,
                "max": 18.17,
                "night": 11.09,
                "eve": 14.05,
                "morn": 10.93
            },
            "feels_like": {
                "day": 15.77,
                "night": 10.34,
                "eve": 13.16,
                "morn": 9.59
            },
            "pressure": 1019,
            "humidity": 38,
            "dew_point": 1.69,
            "wind_speed": 2.16,
            "wind_deg": 299,
            "wind_gust": 3.29,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "clouds": 64,
            "pop": 0,
            "uvi": 2.41
        },
        {
            "dt": 1669662000,
            "sunrise": 1669647689,
            "sunset": 1669683098,
            "moonrise": 1669665540,
            "moonset": 1669701840,
            "moon_phase": 0.18,
            "temp": {
                "day": 14.58,
                "min": 9.84,
                "max": 14.83,
                "night": 9.84,
                "eve": 11.23,
                "morn": 11.21
            },
            "feels_like": {
                "day": 13.69,
                "night": 7.2,
                "eve": 10.11,
                "morn": 10.58
            },
            "pressure": 1015,
            "humidity": 61,
            "dew_point": 6.59,
            "wind_speed": 6.32,
            "wind_deg": 290,
            "wind_gust": 10.41,
            "weather": [
                {
                    "id": 804,
                    "main": "Clouds",
                    "description": "overcast clouds",
                    "icon": "04d"
                }
            ],
            "clouds": 100,
            "pop": 0,
            "uvi": 2.13
        },
        {
            "dt": 1669748400,
            "sunrise": 1669734148,
            "sunset": 1669769481,
            "moonrise": 1669754100,
            "moonset": 1669792620,
            "moon_phase": 0.22,
            "temp": {
                "day": 10.13,
                "min": 6.56,
                "max": 12.69,
                "night": 8.05,
                "eve": 12.4,
                "morn": 7.37
            },
            "feels_like": {
                "day": 8.5,
                "night": 8.05,
                "eve": 10.53,
                "morn": 5.93
            },
            "pressure": 1018,
            "humidity": 50,
            "dew_point": -0.29,
            "wind_speed": 3.12,
            "wind_deg": 318,
            "wind_gust": 6.34,
            "weather": [
                {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "clouds": 3,
            "pop": 0,
            "uvi": 2.49
        },
        {
            "dt": 1669834800,
            "sunrise": 1669820605,
            "sunset": 1669855867,
            "moonrise": 1669842360,
            "moonset": 0,
            "moon_phase": 0.25,
            "temp": {
                "day": 11.22,
                "min": 6.12,
                "max": 14.23,
                "night": 8.75,
                "eve": 12.94,
                "morn": 6.52
            },
            "feels_like": {
                "day": 9.05,
                "night": 8.75,
                "eve": 11.39,
                "morn": 6.52
            },
            "pressure": 1020,
            "humidity": 25,
            "dew_point": -8.68,
            "wind_speed": 1.62,
            "wind_deg": 282,
            "wind_gust": 2.74,
            "weather": [
                {
                    "id": 803,
                    "main": "Clouds",
                    "description": "broken clouds",
                    "icon": "04d"
                }
            ],
            "clouds": 52,
            "pop": 0,
            "uvi": 3
        },
        {
            "dt": 1669921200,
            "sunrise": 1669907062,
            "sunset": 1669942254,
            "moonrise": 1669930440,
            "moonset": 1669883100,
            "moon_phase": 0.29,
            "temp": {
                "day": 11.65,
                "min": 8.16,
                "max": 12.55,
                "night": 8.34,
                "eve": 11.19,
                "morn": 8.72
            },
            "feels_like": {
                "day": 10.54,
                "night": 7.03,
                "eve": 10.3,
                "morn": 8.19
            },
            "pressure": 1016,
            "humidity": 64,
            "dew_point": 4.42,
            "wind_speed": 4.88,
            "wind_deg": 177,
            "wind_gust": 10.67,
            "weather": [
                {
                    "id": 501,
                    "main": "Rain",
                    "description": "moderate rain",
                    "icon": "10d"
                }
            ],
            "clouds": 100,
            "pop": 1,
            "rain": 6.88,
            "uvi": 3
        },
        {
            "dt": 1670007600,
            "sunrise": 1669993518,
            "sunset": 1670028644,
            "moonrise": 1670018280,
            "moonset": 1669973520,
            "moon_phase": 0.33,
            "temp": {
                "day": 10.29,
                "min": 6.79,
                "max": 12.15,
                "night": 8.19,
                "eve": 11.58,
                "morn": 7.41
            },
            "feels_like": {
                "day": 9.1,
                "night": 8.19,
                "eve": 10.15,
                "morn": 7.41
            },
            "pressure": 1023,
            "humidity": 66,
            "dew_point": 3.7,
            "wind_speed": 1.78,
            "wind_deg": 357,
            "wind_gust": 2.31,
            "weather": [
                {
                    "id": 501,
                    "main": "Rain",
                    "description": "moderate rain",
                    "icon": "10d"
                }
            ],
            "clouds": 3,
            "pop": 0.96,
            "rain": 3.41,
            "uvi": 3
        },
        {
            "dt": 1670094000,
            "sunrise": 1670079973,
            "sunset": 1670115035,
            "moonrise": 1670106180,
            "moonset": 1670063760,
            "moon_phase": 0.36,
            "temp": {
                "day": 10.66,
                "min": 6.03,
                "max": 12.04,
                "night": 9.65,
                "eve": 11.71,
                "morn": 6.69
            },
            "feels_like": {
                "day": 9.17,
                "night": 8.87,
                "eve": 10.71,
                "morn": 6.69
            },
            "pressure": 1018,
            "humidity": 53,
            "dew_point": 0.8,
            "wind_speed": 4.1,
            "wind_deg": 172,
            "wind_gust": 9.69,
            "weather": [
                {
                    "id": 500,
                    "main": "Rain",
                    "description": "light rain",
                    "icon": "10d"
                }
            ],
            "clouds": 4,
            "pop": 0.69,
            "rain": 2.77,
            "uvi": 3
        }
    ]
}
    """

    const val CITY_RESPONSE = """
        [
    {
        "name": "Belgrade",
        "local_names": {
            "th": "เบลเกรด",
            "ascii": "Beograd",
            "gv": "Belgraaid",
            "yo": "Belgrade",
            "lt": "Belgradas",
            "kv": "Белград",
            "tt": "Белград",
            "kk": "Белград",
            "ca": "Belgrad",
            "ta": "பெல்கிறேட்",
            "ro": "Belgrad",
            "cs": "Bělehrad",
            "mn": "Белград",
            "li": "Belgrado",
            "jv": "Beograd",
            "sc": "Belgrado",
            "nl": "Belgrado",
            "la": "Belgradum",
            "no": "Beograd",
            "nn": "Beograd",
            "hi": "बॅलग्रेड",
            "mr": "बेलग्रेड",
            "lv": "Belgrada",
            "ty": "Beograd",
            "ur": "بلغراد",
            "ug": "بېلگراد",
            "bg": "Белград",
            "mt": "Belgrad",
            "ga": "Béalgrád",
            "af": "Belgrado",
            "os": "Белград",
            "bs": "Beograd",
            "ht": "Bèlgrad",
            "it": "Belgrado",
            "da": "Beograd",
            "oc": "Belgrad",
            "tl": "Belgrade",
            "tr": "Belgrad",
            "sv": "Belgrad",
            "pt": "Belgrado",
            "ar": "بلغراد",
            "de": "Belgrad",
            "so": "Belgaraad",
            "mk": "Белград",
            "yi": "בעלגראד",
            "en": "Belgrade",
            "fy": "Belgrado",
            "qu": "Beograd",
            "fi": "Belgrad",
            "cy": "Beograd",
            "el": "Βελιγράδι",
            "sm": "Belgrade",
            "sl": "Beograd",
            "feature_name": "Beograd",
            "sr": "Београд",
            "ms": "Belgrade",
            "ie": "Beograd",
            "az": "Belqrad",
            "es": "Belgrado",
            "et": "Belgrad",
            "fr": "Belgrade",
            "hr": "Beograd",
            "sk": "Belehrad",
            "hu": "Belgrád",
            "eo": "Beogrado",
            "cu": "Бѣлъ Градъ",
            "mi": "Belgrade",
            "io": "Belgrade",
            "bo": "བེལ་གེ་རེ་ཌི།",
            "fo": "Beograd",
            "ko": "베오그라드",
            "vi": "Beograd",
            "vo": "Beograd",
            "ja": "ベオグラード",
            "ka": "ბელგრადი",
            "pl": "Belgrad",
            "br": "Beograd",
            "ru": "Белград",
            "kn": "ಬೆಲ್ಗ್ರಾದ್",
            "lb": "Belgrad",
            "be": "Бялград",
            "tg": "Белград",
            "is": "Belgrad",
            "ml": "ബെൽഗ്രേഡ്",
            "gl": "Belgrado",
            "sh": "Beograd",
            "hy": "Բելգրադ",
            "fa": "بلگراد",
            "id": "Beograd",
            "bn": "বেলগ্রেড",
            "an": "Belgrado",
            "gd": "Belgrade",
            "zh": "贝尔格莱德/貝爾格勒/貝爾格萊德",
            "cv": "Белград",
            "uk": "Белград",
            "he": "בלגרד",
            "ku": "Belgrad",
            "am": "በልግራድ"
        },
        "lat": 44.8178131,
        "lon": 20.4568974,
        "country": "RS",
        "state": "Central Serbia"
    },
    {
        "name": "Belgrade",
        "lat": 45.773279,
        "lon": -111.184535,
        "country": "US",
        "state": "Montana"
    },
    {
        "name": "Belgrade",
        "lat": 45.4530207,
        "lon": -95.0044608,
        "country": "US",
        "state": "Minnesota"
    },
    {
        "name": "City of Belgrade",
        "local_names": {
            "id": "Beograd",
            "gl": "Belgrado",
            "no": "Beograd",
            "th": "เบลเกรด",
            "mr": "बेलग्रेड",
            "tg": "Белград",
            "ja": "ベオグラード",
            "en": "City of Belgrade",
            "io": "Belgrade",
            "zh": "贝尔格莱德市/貝爾格勒市/貝爾格萊德",
            "uk": "Белград",
            "he": "בלגרד",
            "eo": "Beogrado",
            "cu": "Бѣлъ Градъ · Срьбїи",
            "pt": "Cidade de Belgrado",
            "hi": "बॅलग्रेड",
            "ka": "ბელგრადი",
            "jv": "Beograd",
            "fa": "بلگراد",
            "bo": "བེལ་གེ་རེ་ཌི།",
            "ga": "Béalgrád",
            "la": "Belgradum",
            "tl": "Belgrade",
            "gv": "Belgraaid",
            "li": "Belgrado",
            "sl": "Beograd",
            "so": "Belgaraad",
            "af": "Belgrado",
            "hr": "Grad Beograd",
            "ty": "Beograd",
            "yi": "בעלגראד",
            "ca": "Belgrad",
            "yo": "Belgrade",
            "ml": "ബെൽഗ്രേഡ്",
            "vo": "Beograd",
            "it": "Belgrado",
            "bs": "Grad Beograd",
            "qu": "Beograd",
            "bg": "Град Белград",
            "ro": "Belgrad",
            "cv": "Белград",
            "ur": "بلغراد",
            "tt": "Белград",
            "sq": "Belgradi",
            "ms": "Belgrade",
            "os": "Белград",
            "an": "Belgrado",
            "fr": "Ville de Belgrade",
            "sh": "Beograd",
            "be": "Горад Бялград",
            "mi": "Belgrade",
            "es": "Belgrado",
            "nl": "Stad Belgrado",
            "ie": "Beograd",
            "gd": "Belgrade",
            "hy": "Բելգրադ",
            "ta": "பெல்கிறேட்",
            "da": "Beograd",
            "ug": "بېلگراد",
            "sm": "Belgrade",
            "sr": "Град Београд",
            "lv": "Belgrada",
            "kv": "Белград",
            "hu": "Belgrád",
            "de": "Stadt Belgrad",
            "vi": "Beograd",
            "ar": "بلغراد",
            "pl": "Belgrad",
            "sv": "Belgrad",
            "fo": "Beograd",
            "az": "Belqrad",
            "kk": "Белград",
            "nn": "Beograd",
            "sk": "Belehgrad",
            "ru": "Город Белград",
            "ko": "베오그라드",
            "cs": "Bělehrad",
            "am": "በልግራድ",
            "sc": "Belgrado",
            "bn": "বেলগ্রেড",
            "br": "Beograd",
            "el": "Πόλη του Βελιγραδίου",
            "cy": "Beograd",
            "ht": "Bèlgrad",
            "fy": "Belgrado",
            "lt": "Belgradas",
            "mk": "Град Белград",
            "mn": "Белград"
        },
        "lat": 44.680242050000004,
        "lon": 20.381755966161457,
        "country": "RS",
        "state": "Central Serbia"
    },
    {
        "name": "Belgrade",
        "lat": 44.447507,
        "lon": -69.833221,
        "country": "US",
        "state": "Maine"
    }
]
    """
}