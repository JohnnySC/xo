package com.github.johnnysc.xoandroid.presentation.main

/**
 * @author Asatryan on 18.01.20
 */
interface ScreenChangeListener {

    fun showScreen(screenId: Int)
}

const val EXIT_CODE = -1
const val SETTINGS_SCREEN_ID = 0
const val GAME_SCREEN_ID = 1