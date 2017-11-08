package com.byoutline.secretsauce

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.annotation.IdRes

/**
 * Group of static variables that are required by SecretSauce classes.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
object Settings {
    var DEBUG: Boolean = false
    var CONTAINER_VIEW_ID: Int = 0
    var LOG_PREFIX = "SecretS"
    var viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = {
        throw IllegalStateException("You must init viewModelFactoryProvider")
    }


    fun set(debug: Boolean = DEBUG,
            @IdRes containerViewId: Int = CONTAINER_VIEW_ID,
            viewModelFactoryProvider: (ctx: Context) -> ViewModelProvider.Factory = this.viewModelFactoryProvider) {
        DEBUG = debug
        CONTAINER_VIEW_ID = containerViewId
        this.viewModelFactoryProvider = viewModelFactoryProvider
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider")
    }
}
