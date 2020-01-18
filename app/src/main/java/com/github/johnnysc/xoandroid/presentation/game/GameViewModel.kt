package com.github.johnnysc.xoandroid.presentation.game

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.xoandroid.R
import com.github.johnnysc.xoandroid.data.CellDTO
import com.github.johnnysc.xoandroid.presentation.App
import com.github.johnnysc.xoandroid.presentation.App.Companion.DRAW
import com.github.johnnysc.xoandroid.presentation.App.Companion.WIN
import com.github.johnnysc.xoandroid.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 18.01.20
 */
class GameViewModel(app: Application) : BaseViewModel(app) {

    val messageLiveData = MutableLiveData<String>()
    val timeLiveData = MutableLiveData<String>()
    val cellsLiveData = MutableLiveData<ArrayList<CellUI>>()

    private val timer = object : CountDownTimer(5000, 1000) {
        override fun onFinish() {
            updateData()
        }

        override fun onTick(millisUntilFinished: Long) {
            timeLiveData.postValue((millisUntilFinished / 1000).toString())
        }
    }

    fun startNewGame() {
        viewModelScope.launch {
            try {
                val game = getRepository().startNewGame()
                cellsLiveData.postValue(makeCellsUi(game.cells))
                if (game.firstPlayerId == 0L || game.secondPlayerId == 0L) {
                    messageLiveData.postValue(app.getString(R.string.waiting_second_player_message))
                    startTimer()
                } else {
                    if (game.nowPlayingPlayerId == getRepository().getUserId()) {
                        messageLiveData.postValue(app.getString(R.string.your_turn_message))
                    } else {
                        messageLiveData.postValue(app.getString(R.string.waiting_second_player_to_make_step_message))
                        startTimer()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(app, R.string.generic_error_message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startTimer() {
        getRepository().gameCache?.let {
            if (it.nowPlayingPlayerId != getRepository().getUserId()) {
                timer.cancel()
                if (it.state != WIN || it.state != DRAW)
                    timer.start()
            }
        }
    }

    private fun updateData() {
        timeLiveData.postValue("")
        handleData(true)
    }

    private fun getRepository() = (app as App).repository

    private fun makeCellsUi(cellsDTO: ArrayList<CellDTO>): ArrayList<CellUI> {
        val list = ArrayList<CellUI>()
        for (i in 0..8) {
            val cell = cellsDTO.find { it.id == i }
            if (cell == null)
                list.add(CellUI(i))
            else
                list.add(CellUI(i, if (cell.playerId == getRepository().gameCache?.firstPlayerId) "X" else "O"))
        }
        return list
    }

    fun tapOnCell(id: Int) {
        if (getRepository().gameCache?.cells?.find { it.id == id } == null &&
            getRepository().gameCache?.nowPlayingPlayerId == getRepository().getUserId())
            handleData(false, id)
    }

    private fun handleData(update: Boolean, id: Int = 0) {
        viewModelScope.launch {
            try {
                val game = if (update) getRepository().getUpdate() else getRepository().fillCell(id)
                cellsLiveData.postValue(makeCellsUi(game.cells))
                if (game.firstPlayerId == 0L || game.secondPlayerId == 0L) {
                    messageLiveData.postValue(app.getString(R.string.waiting_second_player_message))
                    startTimer()
                } else {
                    if (game.state == WIN) {
                        if (game.winnderId == getRepository().getUserId())
                            messageLiveData.postValue(app.getString(R.string.you_win_message))
                        else
                            messageLiveData.postValue(app.getString(R.string.you_lose_message))
                    } else if (game.state == DRAW) {
                        messageLiveData.postValue(app.getString(R.string.draw_message))
                    } else {
                        if (game.nowPlayingPlayerId == getRepository().getUserId()) {
                            messageLiveData.postValue(app.getString(R.string.your_turn_message))
                        } else {
                            messageLiveData.postValue(app.getString(R.string.waiting_second_player_to_make_step_message))
                            startTimer()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(app, R.string.generic_error_message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

data class CellUI(val id: Int, val text: String = "")