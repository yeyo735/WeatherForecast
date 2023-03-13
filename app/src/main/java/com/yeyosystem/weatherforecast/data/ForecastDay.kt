package com.yeyosystem.weatherforecast.data

import java.util.Date

data class ForecastDay(
    val date: String,
    val dateEpoch: Long,
    val day: Day,
    //val astro: Astro,
    //val hour: List<Hour>
)
