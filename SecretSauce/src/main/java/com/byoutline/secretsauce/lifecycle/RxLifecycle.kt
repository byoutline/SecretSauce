package com.byoutline.secretsauce.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

fun androidx.fragment.app.FragmentActivity.invokeOnStopped(action: () -> Unit) {
    val activity = this
    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(a: Activity?, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(a: Activity?) {}

        override fun onActivityResumed(a: Activity?) {}
        override fun onActivityPaused(a: Activity?) {}
        override fun onActivityStopped(a: Activity?) {
            if (a === activity) action()
        }

        override fun onActivitySaveInstanceState(a: Activity?, outState: Bundle?) {}
        override fun onActivityDestroyed(a: Activity?) {
            if (a === activity) application.unregisterActivityLifecycleCallbacks(this)
        }
    })
}

fun androidx.fragment.app.Fragment.invokeOnStopped(action: () -> Unit) {
    val fragment = this
    activity!!.supportFragmentManager.registerFragmentLifecycleCallbacks(object :
        androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentStopped(fm: androidx.fragment.app.FragmentManager, f: androidx.fragment.app.Fragment) {
            if (f === fragment) action()
        }

        override fun onFragmentDestroyed(fm: androidx.fragment.app.FragmentManager, f: androidx.fragment.app.Fragment) {
            if (f === fragment) fm.unregisterFragmentLifecycleCallbacks(this)
        }
    }, false)
}
