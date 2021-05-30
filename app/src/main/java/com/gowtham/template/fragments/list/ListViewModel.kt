package com.gowtham.template.fragments.list

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.template.models.Country
import com.gowtham.template.repo.MainRepository
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mainRepo: MainRepository
) : ViewModel() {

    private val _resultState = MutableStateFlow<LoadState>(LoadState.OnLoading)

    var lastQuery: String = ""

    val lastFetchedList = MutableLiveData(emptyList<Country>())
    val showQueryEmptyView = MutableLiveData(false)

    val state: StateFlow<LoadState>
        get() = _resultState

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
            lastFetchedList.value = result.data as List<Country>
            showQueryEmptyView.value = lastFetchedList.value.isNullOrEmpty()
        }
        _resultState.value = result
    }

    private suspend fun fetchAllCountries() {
        showQueryEmptyView.value = false
        val result = mainRepo.getAllCountries()
        if (result is LoadState.OnSuccess)
            lastFetchedList.value = result.data as List<Country>
        _resultState.value = result
    }


     fun searchCountry(searchQuery: Editable?) = viewModelScope.launch {
        lastQuery=searchQuery.toString().trim()
        if (lastQuery.isEmpty())
            fetchAllCountries()
        else
            fetchQueriedCountries(lastQuery)
    }

    fun retry() {
        viewModelScope.launch {
            _resultState.value = LoadState.OnLoading
            fetchAllCountries()
        }
    }

}