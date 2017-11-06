package com.byoutline.secretsauce

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


    fun set(debug: Boolean, @IdRes containerViewId: Int) {
        DEBUG = debug
        CONTAINER_VIEW_ID = containerViewId
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider")
    }
}

