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

/**
 * Creates [AttachableViewModel] and registers it in [FragmentActivity] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onActivityStarted`!*
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> FragmentActivity.getViewModelWithAutoLifecycle(
        view: VIEW): VIEWMODEL {
    val viewModel = getViewModel<VIEWMODEL>()
    application.registerActivityLifecycleCallbacks(ViewModelAutoLifecycleA(application, view, viewModel))
    return viewModel
}

/**
 * Convenience method to get [VIEWMODEL] using Activity context and ViewModelProvider
 */
inline fun <reified VIEWMODEL : ViewModel> FragmentActivity.getViewModel(): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(this)
    return ViewModelProviders.of(this, factory).get(VIEWMODEL::class.java)
}

/**
 * Creates [AttachableViewModel] and registers it in [Fragment] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onFragmentStarted`!*
 *
 * By default activity scoped provider will be used. If you want to use fragment scope instead
 * (fragment passed to [ViewModelProviders.of]), set [useFragmentViewModelProvider] to true.
 */
fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>,
        useFragmentViewModelProvider: Boolean = false
): VIEWMODEL {
    val viewModel = getViewModel(modelClass, useFragmentViewModelProvider)
    val auto = ViewModelAutoLifecycleF(view, viewModel)
    activity!!.supportFragmentManager!!.registerFragmentLifecycleCallbacks(auto, false)
    return viewModel
}

/**
 * Convenience method to get [VIEWMODEL] using Fragment context and ViewModelProvider.
 *
 * By default activity scoped provider will be used. If you want to use fragment scope instead
 * (fragment passed to [ViewModelProviders.of]), set [useFragmentViewModelProvider] to true.
 */
fun <VIEWMODEL : ViewModel> Fragment.getViewModel(modelClass: KClass<VIEWMODEL>, useFragmentViewModelProvider: Boolean=false): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(context!!)
    val provider = if(useFragmentViewModelProvider) {
        ViewModelProviders.of(this, factory)
    } else {
        ViewModelProviders.of(activity!!, factory)
    }
    return provider.get(modelClass.java)
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
 *
 * ## Important
 * For viewModel to get attached it must be referenced before `onFragmentStarted`.
 * Usually this is done in `onCreateView`.
 * If you do not need viewModel in `onCreateView` then use `getViewModelWithAutoLifecycle` directly instead.
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.lazyViewModelWithAutoLifecycle(
        view: VIEW,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModelWithAutoLifecycle(view, VIEWMODEL::class)
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
 *
 * ## Important
 * For viewModel to get attached it must be referenced before `onActivityStarted`.
 * Usually this is done in `onCreate`.
 * If you do not need viewModel in `onCreate` then use `getViewModelWithAutoLifecycle` directly instead.
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> FragmentActivity.lazyViewModelWithAutoLifecycle(
        view: VIEW,
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModelWithAutoLifecycle<VIEWMODEL, VIEW>(view)
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
inline fun <reified VIEWMODEL : ViewModel> FragmentActivity.lazyViewModel(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModel<VIEWMODEL>()
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

/**
 * Alias for [DataBindingUtil.setContentView]
 */
fun <BINDING : ViewDataBinding> FragmentActivity.bindContentView(@LayoutRes layoutId: Int): BINDING =
        DataBindingUtil.setContentView(this, layoutId)