package com.github.johnnysc.xoandroid.presentation.main

import android.app.Application
import android.os.Bundle
import com.github.johnnysc.xoandroid.data.PrefManager
import com.github.johnnysc.xoandroid.presentation.App
import com.github.johnnysc.xoandroid.presentation.core.BaseViewModel

/**
 * @author Asatryan on 18.01.20
 */
class MainViewModel(app: Application) : BaseViewModel(app) {

    fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            if ((app as App).repository.prefManager.readString(PrefManager.HOST).isNullOrEmpty())
                navigateLiveData.value = SETTINGS_SCREEN_ID
            else
                navigateLiveData.value = GAME_SCREEN_ID
        }
    }

}