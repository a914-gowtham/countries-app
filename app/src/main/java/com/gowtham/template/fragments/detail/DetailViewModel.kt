package com.gowtham.template.fragments.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.template.repo.MainRepository
import com.gowtham.template.repo.WeatherRepo
import com.gowtham.template.utils.LogMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    init {
        LogMessage.v("DetailViewModel init")
    }


    fun callWeatherApi() = viewModelScope.launch {
        weatherRepo.getWeatherByCity("delhi")
    }



}