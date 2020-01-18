package com.github.johnnysc.xoandroid.presentation.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @author Asatryan on 18.01.20
 */
abstract class BaseViewModel(protected val app: Application) : AndroidViewModel(app) {

    val navigateLiveData = MutableLiveData<Int>()

}