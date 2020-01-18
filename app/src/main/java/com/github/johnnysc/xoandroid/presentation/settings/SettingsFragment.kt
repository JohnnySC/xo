package com.github.johnnysc.xoandroid.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.johnnysc.xoandroid.R
import com.github.johnnysc.xoandroid.presentation.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * @author Asatryan on 18.01.20
 */
class SettingsFragment : BaseFragment<SettingsViewModel>() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun getViewModelClass() = SettingsViewModel::class.java

    override fun getLayoutResId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton.setOnClickListener {
            viewModel.cancel()
        }

        saveButton.setOnClickListener {
            viewModel.save(hostEditText.text.toString())
        }
        viewModel.init(savedInstanceState)
    }

    override fun makeObservables() {
        viewModel.cancelButtonEnabledLiveData.observe(viewLifecycleOwner, Observer {
            cancelButton.isEnabled = it
        })
        viewModel.inputFieldLiveData.observe(viewLifecycleOwner, Observer {
            hostEditText.setText(it)
        })
    }

}