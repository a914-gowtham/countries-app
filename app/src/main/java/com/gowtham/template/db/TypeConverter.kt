package com.gowtham.template.db

import androidx.room.TypeConverter
import com.gowtham.template.models.Currency
import com.gowtham.template.models.Language
import com.gowtham.template.models.RegionalBloc
import com.gowtham.template.models.Translations
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class TypeConverter {

    /* Currency */
    @TypeConverter
    fun fromCurrencyToString(currency: Currency): String {
        return Json.encodeToString(currency)
    }

    @TypeConverter
    fun fromStringToCurrency(currency: String): Currency {
        return Json.decodeFromString(currency)
    }

    /* Language */
    @TypeConverter
    fun fromLanguageToString(language: Language): String {
        return Json.encodeToString(language)
    }

    @TypeConverter
    fun fromStringToLanguage(language: String): Language {
        return Json.decodeFromString(language)
    }

    /* RegionalBloc */
    @TypeConverter
    fun fromRegionalBlocToString(regionalBloc: RegionalBloc): String {
        return Json.encodeToString(regionalBloc)
    }

    @TypeConverter
    fun fromStringToRegionalBloc(regionalBloc: String): RegionalBloc {
        return Json.decodeFromString(regionalBloc)
    }

    /* Translations */
    @TypeConverter
    fun fromTranslationsToString(translations: Translations): String {
        return Json.encodeToString(translations)
    }

    @TypeConverter
    fun fromStringToTranslations(translations: String): Translations {
        return Json.decodeFromString(translations)
    }

    /* List of string */
    @TypeConverter
    fun fromListOfStringToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToListOfString(list: String): List<String> {
        return Json.decodeFromString(list)
    }

    /* List of double */
    @TypeConverter
    fun fromListOfDoubleToString(listOfDouble: List<Double>): String {
        return Json.encodeToString(listOfDouble)
    }

    @TypeConverter
    fun fromStringToListOfDouble(listOfDouble: String): List<Double> {
        return Json.decodeFromString(listOfDouble)
    }

}