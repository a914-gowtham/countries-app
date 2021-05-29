package com.gowtham.template.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegionalBloc(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>,
    val otherNames: List<String>
) : Parcelable