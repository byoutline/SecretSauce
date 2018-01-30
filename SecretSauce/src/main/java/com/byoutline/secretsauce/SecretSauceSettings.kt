package com.byoutline.secretsauce

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.annotation.IdRes

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


    fun set(
            debug: Boolean = DEBUG,
            @IdRes containerViewId: Int = this.containerViewId ?: ID_NOT_SET ,
            bindingViewModelId: Int = brViewModelId ?: ID_NOT_SET,
            viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = this.viewModelFactoryProvider,
            setFastJodaTimeZoneProvider: Boolean = true
    ) {
        require(containerViewId > 0 || containerViewId == ID_NOT_SET) {"containerViewId cannot be negative, given: $containerViewId"}
        require(bindingViewModelId > 0 || bindingViewModelId == ID_NOT_SET) {"containerViewId cannot be negative, given: $containerViewId"}
        if(containerViewId != ID_NOT_SET) this.containerViewId = containerViewId
        if(bindingViewModelId != ID_NOT_SET) brViewModelId = bindingViewModelId

        DEBUG = debug
        this.viewModelFactoryProvider = viewModelFactoryProvider
        if(setFastJodaTimeZoneProvider) {
            System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider")
        }
    }
}

private const val ID_NOT_SET = -877726
