package com.github.johnnysc.xoandroid.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.johnnysc.xoandroid.R
import com.github.johnnysc.xoandroid.presentation.game.GameFragment
import com.github.johnnysc.xoandroid.presentation.settings.SettingsFragment

class MainActivity : AppCompatActivity(), ScreenChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.navigateLiveData.observe(this, Observer {
            showScreen(it)
        })
        viewModel.init(savedInstanceState)
    }

    override fun showScreen(screenId: Int) {
        if (screenId == EXIT_CODE)
            finish()
        else
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    if (screenId == SETTINGS_SCREEN_ID)
                        SettingsFragment.newInstance()
                    else
                        GameFragment.newInstance()
                ).commit()
    }
}