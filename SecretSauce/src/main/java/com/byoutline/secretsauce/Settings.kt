package com.byoutline.secretsauce

import android.content.Context
import android.support.annotation.IdRes
import android.view.inputmethod.InputMethodManager
import com.squareup.otto.Bus

/**
 * Group of static variables that are required by SecretSauce classes.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
object Settings {
    var CONTEXT: Context? = null
    var DEBUG: Boolean = false
    var CONTAINER_VIEW_ID: Int = 0
    var INPUT_MANAGER: InputMethodManager? = null
    var LOG_PREFIX = "SecretS"


    fun set(context: Context, debug: Boolean, @IdRes containerViewId: Int) {
        CONTEXT = context.applicationContext
        DEBUG = debug
        CONTAINER_VIEW_ID = containerViewId
        INPUT_MANAGER = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider")
    }
}

