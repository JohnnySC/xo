package com.github.johnnysc.xoandroid.data

import android.content.Context
import android.preference.PreferenceManager

/**
 * @author Asatryan on 18.01.20
 */
class PrefManager(context: Context) {

    companion object {
        const val USER_ID = "UserId"
        const val HOST = "host"
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun writeString(key: String, value: String?) = sharedPreferences.edit().putString(key, value).apply()

    fun readString(key: String, default: String = ""): String? = sharedPreferences.getString(key, default)

    fun readLong(key: String, default: Long = 0L) = sharedPreferences.getLong(key, default)

    fun writeLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()
}