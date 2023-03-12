package com.yeyosystem.weatherforecast.nertwork

import com.yeyosystem.weatherforecast.data.WeatherData
import retrofit2.http.GET

interface WeatherService {

    @GET("forecast.json?key=9c9eb56cdabc4fb0bf8194702230903&q=London&days=3&aqi=no&alerts=no")
    suspend fun getWeathers(): WeatherData


}