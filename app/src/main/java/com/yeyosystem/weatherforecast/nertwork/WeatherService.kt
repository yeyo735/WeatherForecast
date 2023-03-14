package com.yeyosystem.weatherforecast.nertwork

import com.yeyosystem.weatherforecast.data.Location
import com.yeyosystem.weatherforecast.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    suspend fun getWeathers(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherData

    @GET("search.json")
    suspend fun getCities(
        @Query("key") key: String,
        @Query("q") location: String
    ): List<Location>
}