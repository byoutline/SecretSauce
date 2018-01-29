package com.byoutline.secretsauce.databinding

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.PropertyChangeRegistry
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

/**
 * Short syntax for creating fields that can be observed by databinding
 *
 * Example of usage:
 *  * Example of usage:
 * ```kotlin
 * class ExampleViewModel(): ViewModel, DataBindingObservable by DataBindingObservableImpl() {
 *     @get:Bindable
 *     var field1: Int by observable(0, BR.field1)
 *         private set
 *
 *     @get:Bindable
 *     var field2: String by observable("", BR.field2)
 *         private set
 * }
 * ```
 */
interface DataBindingObservable : Observable {
    fun <T> Observable.observable(initialValue: T, propertyId: Int): ReadWriteProperty<Any?, T>
}

/**
 * Short syntax for creating fields that can be observed by databinding.
 *
 * This may be preferable then using [ObservableField] if you want private setter.
 *
 * Example of usage:
 * ```kotlin
 * class ExampleViewModel(): DataBindingObservableImpl() {
 *     @get:Bindable
 *     var field1: Int by observable(0, BR.field1)
 *         private set
 *
 *     @get:Bindable
 *     var field2: String by observable("", BR.field2)
 *         private set
 * }
 * ```
 */
abstract class DataBindingObservableImpl : DataBindingObservable {
    private val propertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) =
            propertyChangeRegistry.add(callback)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) =
            propertyChangeRegistry.remove(callback)

    override fun <T> Observable.observable(initialValue: T, propertyId: Int) = Delegates.observable(initialValue) { _, _, _ ->
        propertyChangeRegistry.notifyChange(this, propertyId)
    }
}

object DataBindingHelper {
    /**
     * Shorter syntax to create [Observable.OnPropertyChangedCallback] for [ObservableBoolean].
     * Result can be set as listener only to [ObservableBoolean], otherwise it will ignore any
     * *propertyChanged* notifications.
     */
    inline fun observableBooleanCallback(crossinline callback: (Boolean) -> Unit): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (sender !is ObservableBoolean) {
                    return
                }
                val state = sender.get()
                callback(state)
            }
        }
    }

    /**
     * Shorter syntax to create [Observable.OnPropertyChangedCallback] for [ObservableField].
     * Result can be set as listener only to [ObservableField] of matching type [T],
     * otherwise it will ignore any *propertyChanged* notifications.
     */
    inline fun <T> observableFieldCallback(crossinline callback: (T) -> Unit): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (sender !is ObservableField<*>) {
                    return
                }
                val obs: ObservableField<T> = sender as ObservableField<T>
                val state = obs.get()
                callback(state)
            }
        }
    }
}