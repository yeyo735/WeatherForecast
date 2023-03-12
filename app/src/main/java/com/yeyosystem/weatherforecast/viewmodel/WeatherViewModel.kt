package com.yeyosystem.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.yeyosystem.weatherforecast.nertwork.LoadingState
import com.yeyosystem.weatherforecast.nertwork.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherService: WeatherService
) :ViewModel() {
    private val state = MutableLiveData(LoadingState.Loading)
    val weathers = liveData {
        emit(weatherService.getWeathers())
        state.postValue(LoadingState.Loaded)
    }
}