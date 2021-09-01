package com.sector.travelmanager.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class LanguagePreferences(context: Context) {

    companion object {
        private const val LANGUAGE_STATUS = ""
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var setLanguage = preferences.getInt(LANGUAGE_STATUS, 0)
        set(value) = preferences.edit().putInt(LANGUAGE_STATUS, value).apply()
}