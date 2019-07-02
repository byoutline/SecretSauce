package com.byoutline.secretsauce

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import androidx.annotation.IdRes

/**
 * Initializes some variables globally for SecretSauce.
 * As an alternative you usually can pass those values as method optional
 * parameter.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
object SecretSauceSettings {
    var DEBUG: Boolean = false
    private var containerViewId: Int? = null
    internal fun getContainerViewId(): Int = checkNotNull(containerViewId)
    { "Attempt to use method that requires `containerViewId` without setting it first" }

    private var brViewModelId: Int? = null
    internal fun getBrViewModelId(): Int = checkNotNull(brViewModelId)
    { "Attempt to use method that requires `brViewModelId` without setting it first" }

    var logPrefix = "SecretS"
    var viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = {
        throw IllegalStateException("You must init viewModelFactoryProvider")
    }
    var useFragmentViewModelProvider: Boolean = true

    /**
     * @param debug - If true enables debug toasts, and low priority logs in [com.byoutline.secretsauce.utils.LogUtils]
     * @param containerViewId - Default value for [com.byoutline.secretsauce.activities.showFragment]
     * @param bindingViewModelId - Default value for DataBinding methods that set ViewModel
     * @param viewModelFactoryProvider - Required for using getViewModel and related extension function
     * @param setFastJodaTimeZoneProvider - If true System property will be set so Joda time uses [com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider]
     * which is faster on Android. If there is no Joda dependency in classpath this will do nothing.
     * @param useFragmentViewModelProvider - Default lifecycle for ViewModels [com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle]
     */
    fun set(
        debug: Boolean = DEBUG,
        @IdRes containerViewId: Int = this.containerViewId ?: ID_NOT_SET,
        bindingViewModelId: Int = brViewModelId ?: ID_NOT_SET,
        viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = this.viewModelFactoryProvider,
        setFastJodaTimeZoneProvider: Boolean = true,
        useFragmentViewModelProvider: Boolean = this.useFragmentViewModelProvider
    ) {
        require(bindingViewModelId > 0 || bindingViewModelId == ID_NOT_SET) { "containerViewId cannot be negative, given: $containerViewId" }
        if (containerViewId != ID_NOT_SET) this.containerViewId = containerViewId
        if (bindingViewModelId != ID_NOT_SET) brViewModelId = bindingViewModelId

        DEBUG = debug
        this.viewModelFactoryProvider = viewModelFactoryProvider
        this.useFragmentViewModelProvider = useFragmentViewModelProvider
        if (setFastJodaTimeZoneProvider) {
            setFastJodaTimeZoneProvider()
        }
    }

    private fun setFastJodaTimeZoneProvider() {
        try {
            Class.forName("org.joda.time.DateTimeZone")
            System.setProperty(
                "org.joda.time.DateTimeZone.Provider",
                "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider"
            )
        } catch (ignored: ClassNotFoundException) {
        }
    }
}

private const val ID_NOT_SET = -877726
