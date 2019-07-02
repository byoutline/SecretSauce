package com.byoutline.secretsauce.databinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
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
    fun <T> Observable.observable(
        initialValue: T,
        propertyId: Int,
        afterChangeCallback: (oldVal: T, newVal: T) -> Unit
    ): ReadWriteProperty<Any?, T>
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
 *
 * @param propertyChangeRegistry Instance of [PropertyChangeRegistry] to use as a delegate.
 * Passing non-default value can be used if one wants to use `by observable` syntax only for
 * some fields in class.
 */
open class DataBindingObservableImpl(
    private val propertyChangeRegistry: PropertyChangeRegistry = PropertyChangeRegistry()
) : DataBindingObservable {

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) =
        propertyChangeRegistry.add(callback)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) =
        propertyChangeRegistry.remove(callback)

    override fun <T> Observable.observable(initialValue: T, propertyId: Int) =
        Delegates.observable(initialValue) { _, oldVal, newVal ->
            if (oldVal != newVal) {
                propertyChangeRegistry.notifyChange(this, propertyId)
            }
        }

    override fun <T> Observable.observable(
        initialValue: T,
        propertyId: Int,
        afterChangeCallback: (oldVal: T, newVal: T) -> Unit
    ) = Delegates.observable(initialValue) { _, oldVal, newVal ->
        if (oldVal != newVal) {
            propertyChangeRegistry.notifyChange(this, propertyId)
            afterChangeCallback(oldVal, newVal)
        }
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

    /**
     * Shorter syntax to create [Observable.OnPropertyChangedCallback] for [Observable].
     * Result can be set as listener only to [Observable] of matching type [T],
     * otherwise it will ignore any *propertyChanged* notifications.
     * [brs] - fields which change should trigger the callback. (It will be also triggered when id is 0)
     */
    inline fun <reified T : Observable> observableCallback(
        vararg brs: Int,
        crossinline action: T.() -> Unit
    ): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val state = sender as? T ?: return
                if (propertyId == 0 || propertyId in brs) {
                    action(state)
                }
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
 * binding = inflateAndSetVM(inflater, container, R.layout.fragment_projects, viewModel)
 * ```
 * or
 * ```
 * val binding: FragmentProjectsBinding = inflateAndSetVM(inflater, container, R.layout.fragment_projects, viewModel)
 * ```
 * Note: defining bidding type next to definition is encouraged. Otherwise you may need to specify this
 * type in generic parameters instead.
 * @param [brVariableId] is required unless it was set globally via [SecretSauceSettings.set].
 */
fun <BINDING : ViewDataBinding> inflateAndSetVM(
    inflater: LayoutInflater,
    container: ViewGroup?,
    @LayoutRes layoutId: Int,
    viewModel: Any,
    brVariableId: Int = SecretSauceSettings.getBrViewModelId(),
    attachToParent: Boolean = false
): BINDING {
    val binding = DataBindingUtil.inflate<BINDING>(inflater, layoutId, container, attachToParent)
    binding.setVariable(brVariableId, viewModel)
    return binding
}

/**
 * Alias for [DataBindingUtil.setContentView]
 */
fun <BINDING : ViewDataBinding> FragmentActivity.bindContentView(@LayoutRes layoutId: Int): BINDING =
    DataBindingUtil.setContentView(this, layoutId)

/**
 * Sets content view via DataBinding ands sets given [viewModel] as binding variable.
 *
 * @param [brVariableId] is required unless it was set globally via [SecretSauceSettings.set].
 * @see [DataBindingUtil.setContentView]
 */
fun <BINDING : ViewDataBinding> FragmentActivity.bindContentViewAndSetVM(
    @LayoutRes layoutId: Int,
    viewModel: Any,
    brVariableId: Int = SecretSauceSettings.getBrViewModelId()
): BINDING {
    val binding: BINDING = bindContentView(layoutId)
    binding.setVariable(brVariableId, viewModel)
    return binding
}
