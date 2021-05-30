package com.gowtham.template.models.country

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Currency(
    val code: String?,
    val name: String?,
    val symbol: String?
) : Parcelable