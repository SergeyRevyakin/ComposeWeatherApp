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

    const val AIR_QUALITY_RESPONSE = """
        {"coord":{"lon":20.4569,"lat":44.8178},"list":[{"main":{"aqi":1},"components":{"co":220.3,"no":0,"no2":7.11,"o3":54.36,"so2":0.89,"pm2_5":5.22,"pm10":7.65,"nh3":2.76},"dt":1714950000},{"main":{"aqi":1},"components":{"co":216.96,"no":0,"no2":6.6,"o3":47.92,"so2":0.74,"pm2_5":5.06,"pm10":7.31,"nh3":2.38},"dt":1714953600},{"main":{"aqi":1},"components":{"co":213.62,"no":0,"no2":6.6,"o3":40.41,"so2":0.65,"pm2_5":5.1,"pm10":7.26,"nh3":1.85},"dt":1714957200},{"main":{"aqi":1},"components":{"co":213.62,"no":0,"no2":7.37,"o3":32.19,"so2":0.6,"pm2_5":5.46,"pm10":7.74,"nh3":1.44},"dt":1714960800},{"main":{"aqi":1},"components":{"co":213.62,"no":0.01,"no2":8.48,"o3":24.68,"so2":0.62,"pm2_5":6.07,"pm10":8.64,"nh3":1.24},"dt":1714964400},{"main":{"aqi":1},"components":{"co":213.62,"no":0.04,"no2":9.43,"o3":18.42,"so2":0.65,"pm2_5":6.6,"pm10":9.58,"nh3":1.2},"dt":1714968000},{"main":{"aqi":1},"components":{"co":216.96,"no":1.05,"no2":9.43,"o3":13.59,"so2":0.69,"pm2_5":7.24,"pm10":11.13,"nh3":1.41},"dt":1714971600},{"main":{"aqi":1},"components":{"co":220.3,"no":4.02,"no2":7.54,"o3":12.52,"so2":0.75,"pm2_5":8.4,"pm10":13.49,"nh3":1.85},"dt":1714975200},{"main":{"aqi":1},"components":{"co":216.96,"no":3.07,"no2":6.43,"o3":25.75,"so2":0.85,"pm2_5":8.37,"pm10":12.73,"nh3":2.31},"dt":1714978800},{"main":{"aqi":1},"components":{"co":211.95,"no":2.29,"no2":5.36,"o3":38.27,"so2":0.92,"pm2_5":8.37,"pm10":12.24,"nh3":2.34},"dt":1714982400},{"main":{"aqi":1},"components":{"co":208.62,"no":1.59,"no2":4.54,"o3":54.36,"so2":1.09,"pm2_5":8.84,"pm10":12.13,"nh3":2.25},"dt":1714986000},{"main":{"aqi":2},"components":{"co":208.62,"no":0.24,"no2":2.08,"o3":90.84,"so2":1.06,"pm2_5":5.94,"pm10":7.4,"nh3":1.22},"dt":1714989600},{"main":{"aqi":3},"components":{"co":205.28,"no":0.09,"no2":1.2,"o3":100.14,"so2":0.94,"pm2_5":4.53,"pm10":5.57,"nh3":0.72},"dt":1714993200},{"main":{"aqi":3},"components":{"co":203.61,"no":0.08,"no2":1.11,"o3":104.43,"so2":1.19,"pm2_5":4.37,"pm10":5.45,"nh3":0.46},"dt":1714996800},{"main":{"aqi":3},"components":{"co":201.94,"no":0.08,"no2":1.39,"o3":105.86,"so2":2.89,"pm2_5":5.2,"pm10":6.39,"nh3":0.26},"dt":1715000400},{"main":{"aqi":3},"components":{"co":200.27,"no":0.07,"no2":1.93,"o3":107.29,"so2":5.6,"pm2_5":5.86,"pm10":7.13,"nh3":0.28},"dt":1715004000},{"main":{"aqi":3},"components":{"co":198.6,"no":0.07,"no2":2.66,"o3":104.43,"so2":7.03,"pm2_5":5.48,"pm10":6.79,"nh3":0.41},"dt":1715007600},{"main":{"aqi":2},"components":{"co":210.29,"no":0.31,"no2":7.88,"o3":86.55,"so2":7.03,"pm2_5":6.13,"pm10":8.37,"nh3":1.6},"dt":1715011200},{"main":{"aqi":2},"components":{"co":236.99,"no":0.64,"no2":18.34,"o3":64.37,"so2":7.87,"pm2_5":8.78,"pm10":13.1,"nh3":3.26},"dt":1715014800},{"main":{"aqi":2},"components":{"co":267.03,"no":0.13,"no2":28.45,"o3":48.64,"so2":9.89,"pm2_5":12.25,"pm10":18.96,"nh3":4.75},"dt":1715018400},{"main":{"aqi":2},"components":{"co":290.39,"no":0,"no2":32.9,"o3":38.98,"so2":12.28,"pm2_5":15.4,"pm10":23.86,"nh3":5.7},"dt":1715022000},{"main":{"aqi":2},"components":{"co":290.39,"no":0,"no2":30.16,"o3":31.47,"so2":9.18,"pm2_5":16.09,"pm10":25.14,"nh3":5.13},"dt":1715025600},{"main":{"aqi":2},"components":{"co":273.71,"no":0,"no2":22.96,"o3":27.9,"so2":4.53,"pm2_5":13.8,"pm10":22.16,"nh3":4.02},"dt":1715029200},{"main":{"aqi":2},"components":{"co":247,"no":0,"no2":15.25,"o3":29.68,"so2":2.47,"pm2_5":10.08,"pm10":16.17,"nh3":3.2},"dt":1715032800},{"main":{"aqi":1},"components":{"co":226.97,"no":0,"no2":10.2,"o3":32.19,"so2":1.94,"pm2_5":7.05,"pm10":11.04,"nh3":2.91},"dt":1715036400},{"main":{"aqi":1},"components":{"co":211.95,"no":0,"no2":7.2,"o3":34.33,"so2":1.86,"pm2_5":5.26,"pm10":7.93,"nh3":2.79},"dt":1715040000},{"main":{"aqi":1},"components":{"co":205.28,"no":0,"no2":5.57,"o3":36.84,"so2":2.03,"pm2_5":4.18,"pm10":6.09,"nh3":2.5},"dt":1715043600},{"main":{"aqi":1},"components":{"co":201.94,"no":0,"no2":5.36,"o3":33.62,"so2":1.76,"pm2_5":3.93,"pm10":5.75,"nh3":2.01},"dt":1715047200},{"main":{"aqi":1},"components":{"co":201.94,"no":0,"no2":5.66,"o3":29.33,"so2":1.45,"pm2_5":4.04,"pm10":5.98,"nh3":1.63},"dt":1715050800},{"main":{"aqi":1},"components":{"co":201.94,"no":0.03,"no2":6.26,"o3":25.75,"so2":1.21,"pm2_5":4.27,"pm10":6.45,"nh3":1.55},"dt":1715054400},{"main":{"aqi":1},"components":{"co":206.95,"no":0.92,"no2":6.68,"o3":24.68,"so2":1.27,"pm2_5":4.68,"pm10":7.44,"nh3":1.87},"dt":1715058000},{"main":{"aqi":1},"components":{"co":216.96,"no":2.85,"no2":6.94,"o3":28.25,"so2":1.68,"pm2_5":5.48,"pm10":8.95,"nh3":2.31},"dt":1715061600},{"main":{"aqi":1},"components":{"co":223.64,"no":3.55,"no2":7.88,"o3":39.7,"so2":2.98,"pm2_5":6.27,"pm10":9.94,"nh3":2.6},"dt":1715065200},{"main":{"aqi":1},"components":{"co":226.97,"no":3.55,"no2":8.23,"o3":52.21,"so2":3.87,"pm2_5":7.59,"pm10":11.53,"nh3":2.82},"dt":1715068800},{"main":{"aqi":2},"components":{"co":226.97,"no":2.57,"no2":7.2,"o3":69.38,"so2":4.05,"pm2_5":9.7,"pm10":13.83,"nh3":2.88},"dt":1715072400},{"main":{"aqi":3},"components":{"co":201.94,"no":0.34,"no2":1.74,"o3":107.29,"so2":3.49,"pm2_5":7.68,"pm10":9.73,"nh3":0.78},"dt":1715076000},{"main":{"aqi":3},"components":{"co":193.6,"no":0.13,"no2":0.75,"o3":107.29,"so2":1.91,"pm2_5":5.34,"pm10":6.7,"nh3":0.63},"dt":1715079600},{"main":{"aqi":3},"components":{"co":191.93,"no":0.12,"no2":0.68,"o3":105.86,"so2":1.7,"pm2_5":4.56,"pm10":5.75,"nh3":0.63},"dt":1715083200},{"main":{"aqi":3},"components":{"co":191.93,"no":0.16,"no2":1.06,"o3":103,"so2":2.12,"pm2_5":4.37,"pm10":5.68,"nh3":0.79},"dt":1715086800},{"main":{"aqi":2},"components":{"co":193.6,"no":0.24,"no2":1.91,"o3":98.71,"so2":3.7,"pm2_5":4.61,"pm10":6.29,"nh3":1.12},"dt":1715090400},{"main":{"aqi":2},"components":{"co":195.27,"no":0.29,"no2":3.34,"o3":94.41,"so2":7.99,"pm2_5":5.17,"pm10":7.26,"nh3":1.43},"dt":1715094000},{"main":{"aqi":2},"components":{"co":200.27,"no":0.27,"no2":6.68,"o3":83.69,"so2":11.56,"pm2_5":6.23,"pm10":9.19,"nh3":2.31},"dt":1715097600},{"main":{"aqi":2},"components":{"co":213.62,"no":0.25,"no2":13.54,"o3":65.09,"so2":13.35,"pm2_5":8.24,"pm10":12.74,"nh3":3.39},"dt":1715101200},{"main":{"aqi":2},"components":{"co":240.33,"no":0.04,"no2":23.65,"o3":46.49,"so2":15.02,"pm2_5":11.77,"pm10":18.3,"nh3":4.12},"dt":1715104800},{"main":{"aqi":2},"components":{"co":263.69,"no":0,"no2":29.13,"o3":32.19,"so2":13.23,"pm2_5":15.09,"pm10":23.07,"nh3":4.62},"dt":1715108400},{"main":{"aqi":2},"components":{"co":263.69,"no":0,"no2":26.05,"o3":24.68,"so2":7.99,"pm2_5":15.71,"pm10":23.55,"nh3":4.62},"dt":1715112000},{"main":{"aqi":2},"components":{"co":240.33,"no":0,"no2":16.62,"o3":27.18,"so2":4.35,"pm2_5":12.42,"pm10":18.15,"nh3":3.99},"dt":1715115600},{"main":{"aqi":1},"components":{"co":213.62,"no":0,"no2":8.48,"o3":37.55,"so2":2.74,"pm2_5":8.13,"pm10":11.15,"nh3":2.44},"dt":1715119200},{"main":{"aqi":1},"components":{"co":203.61,"no":0,"no2":5.91,"o3":37.91,"so2":2.09,"pm2_5":6.41,"pm10":8.38,"nh3":1.9},"dt":1715122800},{"main":{"aqi":1},"components":{"co":200.27,"no":0,"no2":5.27,"o3":32.19,"so2":2.44,"pm2_5":5.86,"pm10":7.54,"nh3":2.25},"dt":1715126400},{"main":{"aqi":1},"components":{"co":196.93,"no":0.01,"no2":5.57,"o3":25.39,"so2":2.62,"pm2_5":5.96,"pm10":7.72,"nh3":2.88},"dt":1715130000},{"main":{"aqi":1},"components":{"co":198.6,"no":0.03,"no2":6.77,"o3":17.52,"so2":1.61,"pm2_5":6.65,"pm10":8.84,"nh3":2.41},"dt":1715133600},{"main":{"aqi":1},"components":{"co":210.29,"no":0.08,"no2":9.85,"o3":10.82,"so2":1.07,"pm2_5":9.21,"pm10":12.34,"nh3":1.81},"dt":1715137200},{"main":{"aqi":2},"components":{"co":230.31,"no":0.14,"no2":12.85,"o3":8.05,"so2":1.28,"pm2_5":12.86,"pm10":16.95,"nh3":1.39},"dt":1715140800},{"main":{"aqi":2},"components":{"co":233.65,"no":0.66,"no2":12.34,"o3":10.28,"so2":1.31,"pm2_5":13.59,"pm10":18.09,"nh3":1.27},"dt":1715144400},{"main":{"aqi":2},"components":{"co":233.65,"no":1.37,"no2":10.63,"o3":17.52,"so2":1.15,"pm2_5":11.94,"pm10":16.2,"nh3":1.31},"dt":1715148000},{"main":{"aqi":1},"components":{"co":220.3,"no":0.35,"no2":7.45,"o3":52.93,"so2":1.59,"pm2_5":8.78,"pm10":11.15,"nh3":0.84},"dt":1715151600},{"main":{"aqi":2},"components":{"co":213.62,"no":0.5,"no2":5.14,"o3":65.8,"so2":1.36,"pm2_5":6.95,"pm10":8.56,"nh3":0.76},"dt":1715155200},{"main":{"aqi":2},"components":{"co":210.29,"no":0.51,"no2":3.51,"o3":72.24,"so2":1.01,"pm2_5":6.14,"pm10":7.48,"nh3":0.78},"dt":1715158800},{"main":{"aqi":2},"components":{"co":200.27,"no":0.12,"no2":1.06,"o3":90.12,"so2":0.53,"pm2_5":3.48,"pm10":4.17,"nh3":0.28},"dt":1715162400},{"main":{"aqi":2},"components":{"co":196.93,"no":0.04,"no2":0.58,"o3":94.41,"so2":0.38,"pm2_5":2.38,"pm10":2.95,"nh3":0.17},"dt":1715166000},{"main":{"aqi":2},"components":{"co":195.27,"no":0.02,"no2":0.55,"o3":94.41,"so2":0.33,"pm2_5":2.07,"pm10":2.64,"nh3":0.16},"dt":1715169600},{"main":{"aqi":2},"components":{"co":195.27,"no":0.05,"no2":1.8,"o3":82.97,"so2":0.38,"pm2_5":2.06,"pm10":2.94,"nh3":0.93},"dt":1715173200},{"main":{"aqi":2},"components":{"co":198.6,"no":0.08,"no2":4.16,"o3":77.25,"so2":0.54,"pm2_5":2.25,"pm10":3.71,"nh3":2.41},"dt":1715176800},{"main":{"aqi":2},"components":{"co":203.61,"no":0.05,"no2":6.86,"o3":76.53,"so2":0.69,"pm2_5":2.5,"pm10":4.51,"nh3":3.83},"dt":1715180400},{"main":{"aqi":2},"components":{"co":210.29,"no":0.02,"no2":8.74,"o3":75.82,"so2":0.81,"pm2_5":2.7,"pm10":5.07,"nh3":4.75},"dt":1715184000},{"main":{"aqi":2},"components":{"co":210.29,"no":0.01,"no2":7.8,"o3":74.39,"so2":0.82,"pm2_5":2.68,"pm10":5.06,"nh3":4.37},"dt":1715187600},{"main":{"aqi":2},"components":{"co":206.95,"no":0,"no2":5.01,"o3":73.67,"so2":0.67,"pm2_5":2.3,"pm10":4.17,"nh3":2.66},"dt":1715191200},{"main":{"aqi":2},"components":{"co":208.62,"no":0,"no2":3.43,"o3":67.95,"so2":0.4,"pm2_5":2.07,"pm10":3.46,"nh3":1.12},"dt":1715194800},{"main":{"aqi":1},"components":{"co":208.62,"no":0,"no2":4.11,"o3":52.93,"so2":0.32,"pm2_5":2.31,"pm10":4.22,"nh3":1.3},"dt":1715198400},{"main":{"aqi":1},"components":{"co":205.28,"no":0,"no2":4.63,"o3":41.13,"so2":0.31,"pm2_5":2.48,"pm10":4.72,"nh3":1.63},"dt":1715202000},{"main":{"aqi":1},"components":{"co":203.61,"no":0,"no2":5.23,"o3":31.47,"so2":0.34,"pm2_5":2.69,"pm10":4.75,"nh3":2.12},"dt":1715205600},{"main":{"aqi":1},"components":{"co":200.27,"no":0.01,"no2":5.57,"o3":24.68,"so2":0.37,"pm2_5":2.96,"pm10":4.76,"nh3":2.82},"dt":1715209200},{"main":{"aqi":1},"components":{"co":198.6,"no":0.02,"no2":5.91,"o3":20.56,"so2":0.38,"pm2_5":3.37,"pm10":4.98,"nh3":3.83},"dt":1715212800},{"main":{"aqi":1},"components":{"co":195.27,"no":0.03,"no2":6,"o3":17.52,"so2":0.4,"pm2_5":3.68,"pm10":5.06,"nh3":4.43},"dt":1715216400},{"main":{"aqi":1},"components":{"co":193.6,"no":0.05,"no2":5.44,"o3":13.77,"so2":0.54,"pm2_5":3.7,"pm10":4.85,"nh3":3.42},"dt":1715220000},{"main":{"aqi":1},"components":{"co":191.93,"no":0.06,"no2":4.93,"o3":11.36,"so2":0.75,"pm2_5":3.66,"pm10":4.63,"nh3":2.25},"dt":1715223600},{"main":{"aqi":1},"components":{"co":191.93,"no":0.04,"no2":4.33,"o3":14.13,"so2":1.62,"pm2_5":4.26,"pm10":5.12,"nh3":1.25},"dt":1715227200},{"main":{"aqi":1},"components":{"co":195.27,"no":0.07,"no2":4.24,"o3":18.77,"so2":2.65,"pm2_5":4.98,"pm10":6.15,"nh3":0.78},"dt":1715230800},{"main":{"aqi":1},"components":{"co":205.28,"no":0.19,"no2":4.37,"o3":28.25,"so2":3.87,"pm2_5":4.12,"pm10":5.29,"nh3":0.63},"dt":1715234400},{"main":{"aqi":1},"components":{"co":216.96,"no":0.19,"no2":3.3,"o3":48.64,"so2":6.56,"pm2_5":2.54,"pm10":3.21,"nh3":0.44},"dt":1715238000},{"main":{"aqi":1},"components":{"co":220.3,"no":0.21,"no2":3,"o3":56.51,"so2":8.23,"pm2_5":2.71,"pm10":3.39,"nh3":0.52},"dt":1715241600},{"main":{"aqi":2},"components":{"co":216.96,"no":0.22,"no2":2.66,"o3":64.37,"so2":9.18,"pm2_5":3.24,"pm10":3.88,"nh3":0.54},"dt":1715245200},{"main":{"aqi":2},"components":{"co":216.96,"no":0.3,"no2":1.82,"o3":76.53,"so2":8.82,"pm2_5":4.16,"pm10":4.73,"nh3":0.47},"dt":1715248800},{"main":{"aqi":2},"components":{"co":211.95,"no":0.19,"no2":1.39,"o3":85.83,"so2":7.45,"pm2_5":4.36,"pm10":4.83,"nh3":0.38},"dt":1715252400},{"main":{"aqi":2},"components":{"co":210.29,"no":0.13,"no2":1.24,"o3":89.41,"so2":6.97,"pm2_5":4,"pm10":4.43,"nh3":0.34},"dt":1715256000},{"main":{"aqi":2},"components":{"co":206.95,"no":0.08,"no2":1.18,"o3":90.84,"so2":6.02,"pm2_5":3.22,"pm10":3.6,"nh3":0.32},"dt":1715259600},{"main":{"aqi":2},"components":{"co":205.28,"no":0.05,"no2":1.23,"o3":91.55,"so2":5.19,"pm2_5":2.64,"pm10":3.01,"nh3":0.33},"dt":1715263200},{"main":{"aqi":2},"components":{"co":205.28,"no":0.04,"no2":1.48,"o3":90.12,"so2":5.36,"pm2_5":2.24,"pm10":2.61,"nh3":0.36},"dt":1715266800},{"main":{"aqi":2},"components":{"co":206.95,"no":0.03,"no2":1.86,"o3":87.98,"so2":5.78,"pm2_5":2.11,"pm10":2.53,"nh3":0.44},"dt":1715270400},{"main":{"aqi":2},"components":{"co":208.62,"no":0.01,"no2":2.38,"o3":84.4,"so2":6.74,"pm2_5":2.58,"pm10":3.12,"nh3":0.59},"dt":1715274000},{"main":{"aqi":2},"components":{"co":211.95,"no":0,"no2":2.72,"o3":82.25,"so2":7.45,"pm2_5":3.15,"pm10":3.85,"nh3":0.73},"dt":1715277600},{"main":{"aqi":2},"components":{"co":213.62,"no":0,"no2":3,"o3":80.11,"so2":7.99,"pm2_5":3.7,"pm10":4.53,"nh3":0.9},"dt":1715281200},{"main":{"aqi":2},"components":{"co":216.96,"no":0,"no2":3.51,"o3":75.1,"so2":8.46,"pm2_5":4.45,"pm10":5.51,"nh3":1.09},"dt":1715284800},{"main":{"aqi":2},"components":{"co":216.96,"no":0,"no2":3.94,"o3":70.81,"so2":8.58,"pm2_5":5.03,"pm10":6.21,"nh3":1.17},"dt":1715288400},{"main":{"aqi":2},"components":{"co":220.3,"no":0,"no2":4.28,"o3":67.23,"so2":8.23,"pm2_5":5.31,"pm10":6.45,"nh3":1.16},"dt":1715292000}]}
    """

}