package com.melo.allan.poc.util

import android.content.Context

/**
 * Created by allan.melo on 25/05/18.
 */

class PrefsUtil(private val context: Context) {
 var screenName: String
    get() {
        val preferences = context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE)

        return preferences.getString(SCREEN_NAME, "")
    }
    set(value) {
        val preferences = context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SCREEN_NAME, value)

        editor.commit()
    }

    val DEFAULT = "DEFAULT"
    val SCREEN_NAME = "screenName"
}