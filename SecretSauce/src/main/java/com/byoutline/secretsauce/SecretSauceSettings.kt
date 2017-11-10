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
    var containerViewId: Int = 0
    var brViewModelId: Int = 0
    var logPrefix = "SecretS"
    var viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = {
        throw IllegalStateException("You must init viewModelFactoryProvider")
    }


    fun set(debug: Boolean = DEBUG,
            @IdRes containerViewId: Int = this.containerViewId,
            bindingViewModelId: Int = brViewModelId,
            viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = this.viewModelFactoryProvider) {
        DEBUG = debug
        this.containerViewId = containerViewId
        brViewModelId = bindingViewModelId
        this.viewModelFactoryProvider = viewModelFactoryProvider
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider")
    }
}
