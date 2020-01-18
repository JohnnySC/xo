package com.github.johnnysc.xoandroid.presentation.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.johnnysc.xoandroid.presentation.main.ScreenChangeListener

/**
 * @author Asatryan on 18.01.20
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    private var screenChangeListener: ScreenChangeListener? = null

    lateinit var viewModel : T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screenChangeListener = context as ScreenChangeListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of (this).get(getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateLiveData.observe(viewLifecycleOwner, Observer {
            navigateTo(it)
        })
        makeObservables()
    }

    override fun onDetach() {
        super.onDetach()
        screenChangeListener = null
    }

    abstract fun getViewModelClass(): Class<T>

    abstract fun getLayoutResId(): Int

    abstract fun makeObservables()

    protected fun navigateTo(screenId:Int) = screenChangeListener?.showScreen(screenId)

}