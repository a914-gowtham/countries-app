package com.gowtham.template.fragments.list

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mainRepo: MainRepository
) : ViewModel() {

    private val _resultState = MutableStateFlow<LoadState>(LoadState.OnLoading)

    var lastQuery = ""

    val lastFetchedList = MutableLiveData(emptyList<Country>())

    val state: StateFlow<LoadState>
        get() = _resultState

    init {
        LogMessage.v("ListViewModel init")
        loadInitialList()
    }

    private fun loadInitialList() = viewModelScope.launch {
        fetchAllCountries()
    }

    private suspend fun fetchQueriedCountries(query: String) {
        _resultState.value = mainRepo.getQueriedCountries(query)
    }

    private suspend fun fetchAllCountries() {
        val result = mainRepo.getAllCountries()
        if (result is LoadState.OnSuccess)
            lastFetchedList.value = result.data as List<Country>
        _resultState.value = result
    }


    fun searchCountry(searchQuery: String) = viewModelScope.launch {
        lastQuery=searchQuery
        if (searchQuery.isBlank())
            fetchQueriedCountries(searchQuery)
        else
            fetchQueriedCountries(searchQuery)
    }


}