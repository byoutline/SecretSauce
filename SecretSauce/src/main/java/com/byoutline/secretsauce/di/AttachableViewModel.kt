package com.byoutline.secretsauce.di

import android.arch.lifecycle.ViewModel

abstract class AttachableViewModel<T> : ViewModel() {

    var view: T? = null
    private var onDetachAction: (() -> Any)? = null

    open fun onAttach(view: T) {
        onAttach(view, {})
    }

    protected fun onAttach(view: T, onDetachAction: () -> Any = {}) {
        this.view = view
        this.onDetachAction = onDetachAction
    }

    open fun onDetach() {
        onDetachAction?.invoke()
        onDetachAction = null
        view = null
    }
}