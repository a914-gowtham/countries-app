package com.gowtham.template.fragments.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.template.models.country.Country
import com.gowtham.template.repo.WeatherRepo
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val weatherRepo: WeatherRepo,
    @Assisted country: Country?,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _resultState = MutableStateFlow<LoadState>(LoadState.OnFailure(""))

    val state: StateFlow<LoadState>
        get() = _resultState

    init {
        LogMessage.v("DetailViewModel init")
        if(country!=null && !country.capital.isNullOrBlank())
            fetchWeather(country.capital)
    }

    private fun fetchWeather(city: String) = viewModelScope.launch {
        LogMessage.v("fetchWeather $city")
        _resultState.value=LoadState.OnLoading
        _resultState.value= weatherRepo.getWeatherByCity(city)
    }

    @AssistedFactory
    interface Factory {
        fun create(country: Country?, savedStateHandle: SavedStateHandle): DetailViewModel
    }



}