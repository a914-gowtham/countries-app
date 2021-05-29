package com.gowtham.template.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Language(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
) : Parcelable