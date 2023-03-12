package com.yeyosystem.weatherforecast.data

data class WeatherData(val location: Location, val current: Current, val forecast: List<ForecastDay>)
