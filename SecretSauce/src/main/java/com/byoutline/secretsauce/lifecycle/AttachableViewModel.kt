package com.byoutline.secretsauce.lifecycle

import androidx.lifecycle.ViewModel

/**
 * [ViewModel] that needs to communicate with the view [T].
 * [ViewModel] will be informed about attach and detach.
 */
abstract class AttachableViewModel<T> : ViewModel() {

    var view: T? = null
    private val onDetachActions: MutableList<AttachableViewModelDetachAction> = ArrayList()

    /**
     * Called when this [ViewModel] should start refreshing the view.
     */
    open fun onAttach(view: T) {
        this.view = view
    }

    /**
     * [onDetachAction] will be invoked when this [ViewModel] is detached.
     * You can call this method multiple times, each time registering additional action
     * to be triggered.
     * @see [registerDetachActions]
     * @see [onDetach]
     */
    @Synchronized
    protected fun registerDetachAction(onDetachAction: AttachableViewModelDetachAction) {
        registerDetachActions(onDetachAction)
    }

    /**
     * [onDetachAction] will be invoked when this [ViewModel] is detached.
     * You can call this method multiple times, each time registering additional actions
     * to be triggered.
     * @see [registerDetachAction]
     * @see [onDetach]
     */
    @Synchronized
    protected fun registerDetachActions(vararg onDetachAction: AttachableViewModelDetachAction) {
        onDetachActions.addAll(onDetachAction)
    }

    /**
     * This method will be called when this [viewModel] should no longer refresh the view.
     * For [ViewModelAutoLifecycleA] for (both fragment/activity) this will be *onStop*
     * action by default.
     */
    @Synchronized
    open fun onDetach() {
        onDetachActions.forEach { it() }
        onDetachActions.clear()
        view = null
    }
}

typealias AttachableViewModelDetachAction = () -> Any