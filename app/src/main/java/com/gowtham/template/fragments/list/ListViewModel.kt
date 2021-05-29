package com.gowtham.template.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.template.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mainRepo: MainRepository) : ViewModel() {

    init {

    }

    fun callListApi(){
        viewModelScope.launch {
            mainRepo.getAllCountries()
        }
    }


}