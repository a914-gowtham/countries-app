package com.gowtham.template.fragments.detail

import androidx.lifecycle.ViewModel
import com.gowtham.template.repo.MainRepository
import com.gowtham.template.utils.LogMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainRepo: MainRepository
) : ViewModel() {

    init {
        LogMessage.v("DetailViewModel init")
    }





}