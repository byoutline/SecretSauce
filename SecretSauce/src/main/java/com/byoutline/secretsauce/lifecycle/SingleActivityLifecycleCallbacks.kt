package com.byoutline.secretsauce.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Wraps given [Application.ActivityLifecycleCallbacks], so only lifecycle of [this] [Activity]
 * are forwarded. Calling this method will immediately register wrapped callback and it will be
 * automatically unregistered when activity is destroyed.
 *
 * This is supposed to help avoid boilerplate in [Application.ActivityLifecycleCallbacks].
 * For example instead of:
 * ```kotlin
 * class ExampleActivity: AppCompatActivity {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         application.registerActivityLifecycleCallbacks(LifecycleListener(this))
 *     }
 * }
 *
 * class LifecycleListener(val view: Activity): ActivityLifecycleCallbacksAdapter() {
 *     override fun onActivityResumed(a: Activity?) {
 *         if(view === a) doSomething()
 *     }
 *     override fun onActivityDestroyed(a: Activity?) {
 *         if (a === view) view.application.unregisterActivityLifecycleCallbacks(this)
 *     }
 * }
 * ```
 * you can write:
 * ```kotlin
 * class ExampleActivity: AppCompatActivity {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         registerLifecycleCallbacksForThisActivityOnly(LifecycleListener())
 *     }
 * }
 *
 * class LifecycleListener(): ActivityLifecycleCallbacksAdapter() {
 *     override fun onActivityResumed(a: Activity?) {
 *         doSomething()
 *     }
 * }
 * ```
 */
fun Activity.registerLifecycleCallbacksForThisActivityOnly(callbacksToWrap: Application.ActivityLifecycleCallbacks) {
    val wrapped = SingleActivityLifecycleCallbacks(this, callbacksToWrap)
    application.registerActivityLifecycleCallbacks(wrapped)
}

/**
 * Wrapper for regular [Application.ActivityLifecycleCallbacks] that passes lifecycle callbacks
 * only if they concern declared activity. Also it automatically unregister itself from application.
 * This is supposed to help avoid boilerplate in lifecycle callback.
 */
private class SingleActivityLifecycleCallbacks(
    private val view: Activity,
    private val delegate: Application.ActivityLifecycleCallbacks
) : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(a: Activity?, savedInstanceState: Bundle?) {
        if (a === view) delegate.onActivityCreated(a, savedInstanceState)
    }

    override fun onActivityStarted(a: Activity?) {
        if (a === view) delegate.onActivityStarted(a)
    }

    override fun onActivityResumed(a: Activity?) {
        if (a === view) delegate.onActivityResumed(a)
    }

    override fun onActivityPaused(a: Activity?) {
        if (a === view) delegate.onActivityPaused(a)
    }

    override fun onActivityStopped(a: Activity?) {
        if (a === view) delegate.onActivityStopped(a)
    }

    override fun onActivitySaveInstanceState(a: Activity?, outState: Bundle?) {
        if (a === view) delegate.onActivitySaveInstanceState(a, outState)
    }

    override fun onActivityDestroyed(a: Activity?) {
        if (a === view) {
            delegate.onActivityDestroyed(a)
            view.application.unregisterActivityLifecycleCallbacks(this)
        }
    }
}

/**
 * Adapter for [Application.ActivityLifecycleCallbacks] for cases where you want to
 * override just one of the callbacks.
 *
 * Example (before and after):
 *
 * ```kotlin
 * class LifecycleListenerBefore: Application.ActivityLifecycleCallbacks {
 *     override fun onActivityCreated(a: Activity?, savedInstanceState: Bundle?) {}
 *     override fun onActivityStarted(a: Activity?) {}
 *     override fun onActivityResumed(a: Activity?) {
 *         doSomething()
 *     }
 *     override fun onActivityPaused(a: Activity?) {}
 *     override fun onActivityStopped(a: Activity?) {}
 *     override fun onActivitySaveInstanceState(a: Activity?, outState: Bundle?) {}
 *     override fun onActivityDestroyed(a: Activity?) {}
 * }
 *
 * class LifecycleListenerAfter: ActivityLifecycleCallbacksAdapter() {
 *     override fun onActivityResumed(a: Activity?) {
 *         doSomething()
 *     }
 * }
 * ```
 */
abstract class ActivityLifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(a: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(a: Activity?) {}

    override fun onActivityResumed(a: Activity?) {}

    override fun onActivityPaused(a: Activity?) {}
    override fun onActivityStopped(a: Activity?) {}

    override fun onActivitySaveInstanceState(a: Activity?, outState: Bundle?) {}
    override fun onActivityDestroyed(a: Activity?) {}
}

