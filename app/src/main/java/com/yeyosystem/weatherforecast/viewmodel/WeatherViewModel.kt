package com.yeyosystem.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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
    val state = MutableLiveData(LoadingState.Loading)
    val weathers = liveData {
        emit(weatherService.getWeathers())
        state.postValue(LoadingState.Loaded)
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