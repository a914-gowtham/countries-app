package com.gowtham.template.fragments.list

import android.location.Location
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.template.models.ScrollEvent
import com.gowtham.template.models.country.Country
import com.gowtham.template.repo.MainRepository
import com.gowtham.template.repo.WeatherRepo
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mainRepo: MainRepository,
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val _resultState = MutableStateFlow<LoadState>(LoadState.OnLoading)

    private val _weatherState = MutableStateFlow<LoadState>(LoadState.OnFailure(""))

    var lastQuery: String = ""

    private val _showQueryEmptyView = MutableLiveData(false)

    val showQueryEmptyView: LiveData<Boolean> = _showQueryEmptyView // Expose an immutable LiveData

    val state: StateFlow<LoadState>
        get() = _resultState

    val weatherState: StateFlow<LoadState>
        get() = _weatherState

    private val typingHandler = Handler(Looper.getMainLooper())

    init {
        LogMessage.v("ListViewModel init")
        loadInitialList()
    }

    private fun loadInitialList() = viewModelScope.launch {
        _resultState.value = LoadState.OnLoading
        fetchAllCountries()
    }

    private suspend fun fetchQueriedCountries(query: String) {
        val result = mainRepo.getQueriedCountries(query)
        if (result is LoadState.OnSuccess) {
            val list = result.data as List<Country>
            _showQueryEmptyView.value = list.isEmpty()
            LogMessage.v("showQueryEmptyView ${list.isEmpty()}")
        }
        _resultState.value = result
        EventBus.getDefault().post(ScrollEvent(emptyList()))
    }

    private suspend fun fetchAllCountries() {
        _showQueryEmptyView.value = false
        val result = mainRepo.getAllCountries()
        _resultState.value = result
        EventBus.getDefault().post(ScrollEvent(emptyList()))
    }


    fun onTxtChanged(searchQuery: Editable?) {
        lastQuery = searchQuery.toString().trim()
        removeTypingCallbacks()
        typingHandler.postDelayed(typingThread, 300)
    }

    fun retry() {
        viewModelScope.launch {
            _resultState.value = LoadState.OnLoading
            fetchAllCountries()
        }
    }

    private fun searchCountry() = viewModelScope.launch {
        if (lastQuery.isEmpty())
            fetchAllCountries()
        else
            fetchQueriedCountries(lastQuery)
    }

    fun fetchWeatherByLocation(location: Location) = viewModelScope.launch {
        val latLng = "${location.latitude},${location.longitude}"
        _weatherState.value = weatherRepo.getWeatherByCity(latLng)
    }

    fun setWeatherLoadState() {
        if (_weatherState.value is LoadState.OnFailure)
            _weatherState.value = LoadState.OnLoading
    }

    private val typingThread = Runnable {
       searchCountry()
    }

    private fun removeTypingCallbacks() {
        typingHandler.removeCallbacks(typingThread)
    }

}