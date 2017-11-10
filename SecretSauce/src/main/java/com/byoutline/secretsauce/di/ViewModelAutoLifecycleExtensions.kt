package com.byoutline.secretsauce.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.byoutline.secretsauce.SecretSauceSettings
import kotlin.reflect.KClass

fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> FragmentActivity.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val viewModel = getViewModel(modelClass)
    application.registerActivityLifecycleCallbacks(ViewModelAutoLifecycleA(application, view, viewModel))
    return viewModel
}

fun <VIEWMODEL : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(this)
    return ViewModelProviders.of(this, factory).get(modelClass.java)
}

fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val viewModel = getViewModel(modelClass)
    val auto = ViewModelAutoLifecycleF(view, viewModel)
    activity!!.supportFragmentManager!!.registerFragmentLifecycleCallbacks(auto, false)
    return viewModel
}

fun <VIEWMODEL : ViewModel> Fragment.getViewModel(modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(context!!)
    return ViewModelProviders.of(activity!!, factory).get(modelClass.java)
}

fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.lazyViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE) = lazy(mode) {
    getViewModelWithAutoLifecycle(view, modelClass)
}

/**
 * Creates [Lazy] [VIEWMODEL] that calls attach and detach automatically.
 * This method is using [SecretSauceSettings.viewModelFactoryProvider].
 * Example:
 * ```
 * private val viewModel by lazyViewModelWithAutoLifecycle(this, ProjectListViewModel::class)
 * ```
 * By default [LazyThreadSafetyMode.NONE] is used.
 * If you are unsure which thread will call [VIEWMODEL] first use other thread safety mode.
 */
fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> FragmentActivity.lazyViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE) = lazy(mode) {
    getViewModelWithAutoLifecycle(view, modelClass)
}

/**
 * Creates [Lazy] [VIEWMODEL] using [SecretSauceSettings.viewModelFactoryProvider].
 * Example:
 * ```
 * private val viewModel by lazyViewModel(ProjectListViewModel::class)
 * ```
 * By default [LazyThreadSafetyMode.NONE] is used.
 * If you are unsure which thread will call [VIEWMODEL] first use other thread safety mode.
 */
fun <VIEWMODEL : ViewModel> FragmentActivity.lazyViewModel(
        modelClass: KClass<VIEWMODEL>,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE) = lazy(mode) {
    getViewModel(modelClass)
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
fun <VIEWMODEL : AttachableViewModel<VIEW>, BINDING : ViewDataBinding, VIEW> inflateAndSetViewModel(
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