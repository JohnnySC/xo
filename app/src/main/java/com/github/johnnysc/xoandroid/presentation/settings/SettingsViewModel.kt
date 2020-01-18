package com.github.johnnysc.xoandroid.presentation.settings

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.xoandroid.R
import com.github.johnnysc.xoandroid.data.PrefManager
import com.github.johnnysc.xoandroid.presentation.App
import com.github.johnnysc.xoandroid.presentation.core.BaseViewModel
import com.github.johnnysc.xoandroid.presentation.main.EXIT_CODE
import com.github.johnnysc.xoandroid.presentation.main.GAME_SCREEN_ID

/**
 * @author Asatryan on 18.01.20
 */
class SettingsViewModel(app: Application) : BaseViewModel(app) {

    val cancelButtonEnabledLiveData = MutableLiveData<Boolean>()
    val inputFieldLiveData = MutableLiveData<String>()

    fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val prefManager = (app as App).repository.prefManager
            val host = prefManager.readString(PrefManager.HOST)
            if (host.isNullOrEmpty())
                cancelButtonEnabledLiveData.value = false
            else {
                inputFieldLiveData.value = host
                cancelButtonEnabledLiveData.value = true
            }
        }
    }

    fun cancel() {
        navigateLiveData.value = GAME_SCREEN_ID
    }

    fun save(newHostValue: String) {
        if (newHostValue.trim().isNotEmpty()) {
            val prefManager = (app as App).repository.prefManager
            prefManager.writeString(PrefManager.HOST, newHostValue.trim())
            Toast.makeText(app, R.string.please_restart_app_message, Toast.LENGTH_LONG).show()
            navigateLiveData.value = EXIT_CODE
        } else {
            Toast.makeText(app, R.string.empty_host_message, Toast.LENGTH_LONG).show()
        }
    }
}