package com.byoutline.secretsauce.lifecycle

import android.databinding.BaseObservable
import android.databinding.Observable
import android.databinding.ObservableField
import com.byoutline.secretsauce.databinding.DataBindingHelper
import io.reactivex.disposables.Disposable

/**
 * [AttachableViewModel] with convenience extension methods for disposing rx [Disposable]
 * and clearing Android Databinding observable listeners.
 */
abstract class AttachableViewModelRx<T> : AttachableViewModel<T>() {

    /** [Disposable] will be cleared when this *ViewModel* is detached.*/
    @Synchronized
    fun Disposable.disposeOnDetach() {
        registerDetachAction { this.dispose() }
    }

    /** Given [callback] will be attached to [BaseObservable] until *ViewModel is detached */
    @Synchronized
    fun BaseObservable.subscribeTillDetach(callback: Observable.OnPropertyChangedCallback) {
        addOnPropertyChangedCallback(callback)
        registerDetachAction { removeOnPropertyChangedCallback(callback) }
    }

    /** Given [callback] will be attached to [BaseObservable] until *ViewModel is detached */
    @Synchronized
    inline fun ObservableField<T>.subscribeTillDetach(crossinline callback: (T) -> Unit) {
        subscribeTillDetach(DataBindingHelper.observableFieldCallback(callback))
    }
}
