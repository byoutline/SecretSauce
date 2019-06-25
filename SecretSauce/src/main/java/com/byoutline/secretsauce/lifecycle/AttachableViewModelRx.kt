package com.byoutline.secretsauce.lifecycle

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.byoutline.secretsauce.databinding.DataBindingHelper
import io.reactivex.disposables.Disposable

/**
 * [AttachableViewModel] with convenience extension methods for disposing rx [Disposable]
 * and clearing Android Databinding observable listeners.
 */
abstract class AttachableViewModelRx<T> : AttachableViewModel<T>() {

    /** [Disposable] will be cleared when this *ViewModel* is detached.*/
    @Synchronized
    fun Disposable.disposeOnDetach(): Disposable {
        registerDetachAction { this.dispose() }
        return this
    }

    /** Given [callback] will be attached to [BaseObservable] until *ViewModel* is detached */
    @Synchronized
    fun Observable.subscribeTillDetach(callback: Observable.OnPropertyChangedCallback) {
        addOnPropertyChangedCallback(callback)
        registerDetachAction { removeOnPropertyChangedCallback(callback) }
    }

    /** Given [callback] will be attached to [BaseObservable] until *ViewModel* is detached */
    @Synchronized
    inline fun <T> ObservableField<T>.subscribeFieldTillDetach(crossinline callback: (T) -> Unit) {
        subscribeTillDetach(DataBindingHelper.observableFieldCallback(callback))
    }

    /** Given [callback] will be attached to [ObservableBoolean] until *ViewModel* is detached */
    @Synchronized
    inline fun ObservableBoolean.subscribeBoolTillDetach(crossinline callback: (Boolean) -> Unit) {
        subscribeTillDetach(DataBindingHelper.observableBooleanCallback(callback))
    }

    /**
     * Given [action] will be called when one any of fields in [Observable] with matching [brs] is changed.
     * This listener will be automatically removed when *ViewModel* is detached.
     */
    @Synchronized
    inline fun <reified T : Observable> T.subscribeTillDetach(vararg brs: Int, crossinline action: T.() -> Unit) {
        val observableCallback: Observable.OnPropertyChangedCallback =
            DataBindingHelper.observableCallback(*brs, action = action)
        subscribeTillDetach(observableCallback)
    }
}
