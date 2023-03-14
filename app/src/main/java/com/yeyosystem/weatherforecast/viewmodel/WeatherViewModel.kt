package com.yeyosystem.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.yeyosystem.weatherforecast.data.Location
import com.yeyosystem.weatherforecast.data.WeatherData
import com.yeyosystem.weatherforecast.nertwork.LoadingState
import com.yeyosystem.weatherforecast.nertwork.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherService: WeatherService
) : ViewModel() {
    private val key = "9c9eb56cdabc4fb0bf8194702230903"
    private val state = MutableLiveData(LoadingState.Loading)
    val location = MutableLiveData<Location>()
    fun getWeatherForecastForCity(city: String): LiveData<WeatherData> {
        val weathers = liveData {
            emit(weatherService.getWeathers(key, city, 3, "no", "no"))
            state.postValue(LoadingState.Loaded)
        }
        return weathers
    }

    fun getCities(city: String): LiveData<List<Location>> {
        val cities = liveData {
            emit(weatherService.getCities(key, city))
            state.postValue(LoadingState.Loaded)
        }
        return cities
    }

    fun formatDateCurrent(current: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.US)
        val date = inputFormat.parse(current)
        return outputFormat.format(date)
    }

    fun formatDateThird(third: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("EEEE", Locale.US)
        val date = inputFormat.parse(third)
        return outputFormat.format(date)
    }
}