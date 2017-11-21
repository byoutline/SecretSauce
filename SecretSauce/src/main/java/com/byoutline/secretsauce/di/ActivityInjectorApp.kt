package com.byoutline.secretsauce.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

abstract class ActivityInjectorApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun activityInjector() = dispatchingActivityInjector
}
