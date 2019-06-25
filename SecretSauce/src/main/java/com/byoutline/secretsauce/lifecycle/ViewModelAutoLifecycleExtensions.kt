package com.byoutline.secretsauce.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.byoutline.secretsauce.SecretSauceSettings
import kotlin.reflect.KClass

/**
 * Creates [AttachableViewModel] and registers it in [FragmentActivity] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onActivityStarted`!*
 *
 * This method is an alias for [getViewModelWithAutoLifecycle]
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.FragmentActivity.getVMWithAutoLifecycle(
    clazz: KClass<VIEWMODEL>,
    @Suppress("UNCHECKED_CAST")
    view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz")
): VIEWMODEL = getViewModelWithAutoLifecycle(clazz, view)

/**
 * Creates [AttachableViewModel] and registers it in [FragmentActivity] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onActivityStarted`!*
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.FragmentActivity.getViewModelWithAutoLifecycle(
    clazz: KClass<VIEWMODEL>,
    @Suppress("UNCHECKED_CAST")
    view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz")
): VIEWMODEL {
    val viewModel = getViewModel<VIEWMODEL>()
    application.registerActivityLifecycleCallbacks(ViewModelAutoLifecycleA(application, view, viewModel))
    return viewModel
}

/**
 * Convenience method to get [VIEWMODEL] using Activity context and ViewModelProvider
 */
inline fun <reified VIEWMODEL : ViewModel> androidx.fragment.app.FragmentActivity.getViewModel(): VIEWMODEL {
    val factory = SecretSauceSettings.viewModelFactoryProvider(this)
    return ViewModelProviders.of(this, factory).get(VIEWMODEL::class.java)
}

/**
 * Creates [AttachableViewModel] and registers it in [Fragment] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onFragmentStarted`!*
 *
 * @param useFragmentViewModelProvider Declares how long you want your ViewModel to exist - you can either use
 * fragment or activity lifecycle. By default it has value declared in [SecretSauceSettings].
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.Fragment.getVMWithAutoLifecycle(
    modelClass: KClass<VIEWMODEL> = VIEWMODEL::class,
    @Suppress("UNCHECKED_CAST")
    view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $modelClass"),
    useFragmentViewModelProvider: Boolean = SecretSauceSettings.useFragmentViewModelProvider,
    recursive: Boolean = false
): VIEWMODEL = getViewModelWithAutoLifecycle(modelClass, view, useFragmentViewModelProvider, recursive)

/**
 * Creates [AttachableViewModel] and registers it in [Fragment] lifecycle.
 * ViewModel will be unregistered when activity is destroyed.
 *
 * *Remember to call it before `onFragmentStarted`!*
 *
 * @param useFragmentViewModelProvider Declares how long you want your ViewModel to exist - you can either use
 * fragment or activity lifecycle. By default it has value declared in [SecretSauceSettings].
 */
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.Fragment.getViewModelWithAutoLifecycle(
    modelClass: KClass<VIEWMODEL> = VIEWMODEL::class,
    @Suppress("UNCHECKED_CAST")
    view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $modelClass"),
    useFragmentViewModelProvider: Boolean = SecretSauceSettings.useFragmentViewModelProvider,
    recursive: Boolean = false
): VIEWMODEL {
    val viewModel = getViewModel(modelClass, useFragmentViewModelProvider)
    val auto = ViewModelAutoLifecycleF(view, viewModel)
    activity!!.supportFragmentManager!!.registerFragmentLifecycleCallbacks(auto, recursive)
    return viewModel
}

/**
 * Convenience method to get [VIEWMODEL] using Fragment context and ViewModelProvider.
 *
 * @param useFragmentViewModelProvider Declares how long you want your ViewModel to exist - you can either use
 * fragment or activity lifecycle. By default it has value declared in [SecretSauceSettings].
 */
inline fun <reified VIEWMODEL : ViewModel> androidx.fragment.app.Fragment.getViewModel(
    modelClass: KClass<VIEWMODEL> = VIEWMODEL::class,
    useFragmentViewModelProvider: Boolean = SecretSauceSettings.useFragmentViewModelProvider
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
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.Fragment.lazyViewModelWithAutoLifecycle(
    clazz: KClass<VIEWMODEL>,
    @Suppress("UNCHECKED_CAST")
    view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz"),
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE
): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModelWithAutoLifecycle(VIEWMODEL::class, view)
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
inline fun <reified VIEWMODEL : AttachableViewModel<VIEW>, VIEW> androidx.fragment.app.FragmentActivity.lazyViewModelWithAutoLifecycle(
    clazz: KClass<VIEWMODEL>,
    @Suppress("UNCHECKED_CAST") view: VIEW = this as? VIEW
        ?: throw IllegalArgumentException("`this` must be a type of view for viewModel: $clazz"),
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE
): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModelWithAutoLifecycle(clazz, view)
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
inline fun <reified VIEWMODEL : ViewModel> androidx.fragment.app.FragmentActivity.lazyViewModel(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE
): Lazy<VIEWMODEL> = lazy(mode) {
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
inline fun <reified VIEWMODEL : ViewModel> androidx.fragment.app.Fragment.lazyViewModel(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE
): Lazy<VIEWMODEL> = lazy(mode) {
    getViewModel<VIEWMODEL>()
}

