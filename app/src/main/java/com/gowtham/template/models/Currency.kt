package com.gowtham.template.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val code: String,
    val name: String,
    val symbol: String
) : Parcelable