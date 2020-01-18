package com.github.johnnysc.xoandroid.presentation.game

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.johnnysc.xoandroid.R
import com.github.johnnysc.xoandroid.presentation.core.BaseFragment
import com.github.johnnysc.xoandroid.presentation.main.SETTINGS_SCREEN_ID
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * @author Asatryan on 18.01.20
 */
class GameFragment : BaseFragment<GameViewModel>() {

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun getViewModelClass() = GameViewModel::class.java

    override fun getLayoutResId() = R.layout.fragment_game

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newGameButton.setOnClickListener {
            viewModel.startNewGame()
        }

        settingsButton.setOnClickListener {
            navigateTo(SETTINGS_SCREEN_ID)
        }

        cell0Button.setOnClickListener {
            chooseCellWithId(0)
        }
        cell1Button.setOnClickListener {
            chooseCellWithId(1)
        }
        cell2Button.setOnClickListener {
            chooseCellWithId(2)
        }
        cell3Button.setOnClickListener {
            chooseCellWithId(3)
        }
        cell4Button.setOnClickListener {
            chooseCellWithId(4)
        }
        cell5Button.setOnClickListener {
            chooseCellWithId(5)
        }
        cell6Button.setOnClickListener {
            chooseCellWithId(6)
        }
        cell7Button.setOnClickListener {
            chooseCellWithId(7)
        }
        cell8Button.setOnClickListener {
            chooseCellWithId(8)
        }
    }

    override fun makeObservables() {
        viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
            messageTextView.text = it
        })
        viewModel.timeLiveData.observe(viewLifecycleOwner, Observer {
            timerTextView.text = it
        })
        viewModel.cellsLiveData.observe(viewLifecycleOwner, Observer { cells ->
            cells.find { it.id == 0 }?.let {
                cell0Button.text = it.text
            }
            cells.find { it.id == 1 }?.let {
                cell1Button.text = it.text
            }
            cells.find { it.id == 2 }?.let {
                cell2Button.text = it.text
            }
            cells.find { it.id == 3 }?.let {
                cell3Button.text = it.text
            }
            cells.find { it.id == 4 }?.let {
                cell4Button.text = it.text
            }
            cells.find { it.id == 5 }?.let {
                cell5Button.text = it.text
            }
            cells.find { it.id == 6 }?.let {
                cell6Button.text = it.text
            }
            cells.find { it.id == 7 }?.let {
                cell7Button.text = it.text
            }
            cells.find { it.id == 8 }?.let {
                cell8Button.text = it.text
            }
        })
    }

    private fun chooseCellWithId(id:Int) {
        viewModel.tapOnCell(id)
    }
}