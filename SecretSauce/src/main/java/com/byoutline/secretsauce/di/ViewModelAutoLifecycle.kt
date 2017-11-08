package com.byoutline.secretsauce.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.byoutline.secretsauce.Settings
import kotlin.reflect.KClass

fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> FragmentActivity.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val viewModel = getViewModel(modelClass)
    application.registerActivityLifecycleCallbacks(ViewModelAutoLifecycleA(application, view, viewModel))
    return viewModel
}

fun <VIEWMODEL : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val factory = Settings.viewModelFactoryProvider(this)
    return ViewModelProviders.of(this, factory).get(modelClass.java)
}

fun <VIEWMODEL : AttachableViewModel<VIEW>, VIEW> Fragment.getViewModelWithAutoLifecycle(
        view: VIEW,
        modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val viewModel = getViewModel(modelClass)
    val auto = ViewModelAutoLifecycleF(view, viewModel)
    activity!!.supportFragmentManager!!.registerFragmentLifecycleCallbacks(auto, false)
    return viewModel
}

fun <VIEWMODEL : ViewModel> Fragment.getViewModel(modelClass: KClass<VIEWMODEL>): VIEWMODEL {
    val factory = Settings.viewModelFactoryProvider(context!!)
    return ViewModelProviders.of(activity!!, factory).get(modelClass.java)
}

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
        fm.unregisterFragmentLifecycleCallbacks(this)
    }
}

class ViewModelAutoLifecycleA<out VIEWMODEL : AttachableViewModel<VIEW>, VIEW>(
        private val application: Application,
        private val view: VIEW,
        private val viewModel: VIEWMODEL
) : Application.ActivityLifecycleCallbacks {

    // activity
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