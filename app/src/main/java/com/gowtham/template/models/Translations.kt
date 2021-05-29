package com.gowtham.template.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Translations(
    val br: String?,
    val de: String?,
    val es: String?,
    val fa: String?,
    val fr: String?,
    val hr: String?,
    val `it`: String?,
    val ja: String?,
    val nl: String?,
    val pt: String?
) : Parcelable