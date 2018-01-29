package com.byoutline.secretsauce.databinding

import android.arch.lifecycle.ViewModel
import android.databinding.*
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.byoutline.secretsauce.SecretSauceSettings
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
                @Suppress("UNCHECKED_CAST")
                val obs: ObservableField<T> = sender as ObservableField<T>
                val state = obs.get()
                callback(state)
            }
        }
    }
}


/**
 * Inflates view via [BINDING] class and sets [viewModel] into given [brVariableId].
 * This method uses [SecretSauceSettings.brViewModelId] as default argument for variable.
 * It is advice to set it globally in [SecretSauceSettings] (although you can provide this value
 * as argument, it makes method call long enough that it may not be worth using)
 *
 * Example:
 * ```
 * private lateinit var binding: FragmentProjectsBinding
 *
 * binding = inflateAndSetViewModel(inflater, container, R.layout.fragment_projects, viewModel)
 * ```
 * or
 * ```
 * val binding: FragmentProjectsBinding = inflateAndSetViewModel(inflater, container, R.layout.fragment_projects, viewModel)
 * ```
 * Note: defining bidding type next to definition is encouraged. Otherwise you may need to specify this
 * type in generic parameters instead.
 */
fun <VIEWMODEL : ViewModel, BINDING : ViewDataBinding> inflateAndSetViewModel(
        inflater: LayoutInflater,
        container: ViewGroup?,
        @LayoutRes layoutId: Int,
        viewModel: VIEWMODEL,
        brVariableId: Int = SecretSauceSettings.brViewModelId,
        attachToParent: Boolean = false): BINDING {
    val binding = DataBindingUtil.inflate<BINDING>(inflater, layoutId, container, attachToParent)
    binding.setVariable(brVariableId, viewModel)
    return binding
}

/**
 * Alias for [DataBindingUtil.setContentView]
 */
fun <BINDING : ViewDataBinding> FragmentActivity.bindContentView(@LayoutRes layoutId: Int): BINDING =
        DataBindingUtil.setContentView(this, layoutId)