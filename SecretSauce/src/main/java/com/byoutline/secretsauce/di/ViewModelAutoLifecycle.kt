package com.byoutline.secretsauce.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Attaches and detaches [AttachableViewModel] to Fragment.
 */
class ViewModelAutoLifecycleF<out VIEWMODEL : AttachableViewModel<VIEW>, VIEW>(
        val view: VIEW,
        val viewModel: VIEWMODEL
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentStarted(fm: FragmentManager?, f: Fragment?) {
        if (f === view) viewModel.onAttach(view)
    }

    override fun onFragmentStopped(fm: FragmentManager?, f: Fragment?) {
        if (f === view) viewModel.onDetach()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment?) {
        if (f === view) fm.unregisterFragmentLifecycleCallbacks(this)
    }
}

/**
 * Attaches and detaches [AttachableViewModel] to activity.
 */
class ViewModelAutoLifecycleA<out VIEWMODEL : AttachableViewModel<VIEW>, VIEW>(
        private val application: Application,
        private val view: VIEW,
        private val viewModel: VIEWMODEL
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(a: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(a: Activity?) {
        if (a === view) viewModel.onAttach(view)
    }

    override fun onActivityResumed(a: Activity?) {}
    override fun onActivityPaused(a: Activity?) {}
    override fun onActivityStopped(a: Activity?) {
        if (a === view) viewModel.onDetach()
    }

    override fun onActivitySaveInstanceState(a: Activity?, outState: Bundle?) {}
    override fun onActivityDestroyed(a: Activity?) {
        if (a === view) application.unregisterActivityLifecycleCallbacks(this)
    }
}