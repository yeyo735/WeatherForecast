package com.yeyosystem.weatherforecast.data

data class Location(
    val id: Long?,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String?,
    val country: String,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String
)

