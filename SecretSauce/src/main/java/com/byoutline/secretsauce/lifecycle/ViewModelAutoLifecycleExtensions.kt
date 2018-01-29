package com.byoutline.secretsauce.lifecycle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
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
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL> = VIEWMODEL::class,
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
inline fun <reified VIEWMODEL : ViewModel> Fragment.getViewModel(
        modelClass: KClass<VIEWMODEL> = VIEWMODEL::class,
        useFragmentViewModelProvider: Boolean = false
): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(context!!)
    val provider = if (useFragmentViewModelProvider) {
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
 * private val viewModel by lazyViewModelWithAutoLifecycle(ProjectListViewModel::class)
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
        clazz: KClass<VIEWMODEL>,
        @Suppress("UNCHECKED_CAST") view: VIEW = this as? VIEW
                ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz"),
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModelWithAutoLifecycle(view, VIEWMODEL::class)
}

/**
 * Creates [Lazy] [VIEWMODEL] that calls attach and detach automatically.
 * This method is using [SecretSauceSettings.viewModelFactoryProvider].
 * Example:
 * ```
 * private val viewModel by lazyViewModelWithAutoLifecycle(ProjectListViewModel::class)
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
        clazz: KClass<VIEWMODEL>,
        @Suppress("UNCHECKED_CAST") view: VIEW = this as? VIEW
                ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz"),
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
 * Creates [Lazy] [VIEWMODEL] using [SecretSauceSettings.viewModelFactoryProvider].
 * Example:
 * ```
 * val viewModel by lazyViewModel<YourViewModel>()
 * ```
 * By default [LazyThreadSafetyMode.NONE] is used.
 * If you are unsure which thread will call [VIEWMODEL] first use other thread safety mode.
 */
inline fun <reified VIEWMODEL : ViewModel> Fragment.lazyViewModel(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModel<VIEWMODEL>()
}

