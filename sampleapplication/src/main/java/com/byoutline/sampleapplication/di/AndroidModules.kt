package com.byoutline.sampleapplication.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.byoutline.sampleapplication.MainActivity
import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity
import com.byoutline.sampleapplication.databinding.DataBindingActivity
import com.byoutline.sampleapplication.databinding.DataBindingViewModel
import com.byoutline.sampleapplication.draweractivity.*
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.rx.RxLifecycleViewModel
import com.byoutline.sampleapplication.waitlayout.WaitActivity
import com.byoutline.secretsauce.di.ViewModelFactory
import com.byoutline.secretsauce.di.ViewModelKey
import com.byoutline.secretsauce.utils.NetworkChangeReceiver
import com.byoutline.secretsauce.utils.NetworkChangeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector abstract fun mainActivity(): MainActivity
    @ContributesAndroidInjector abstract fun networkChangeActivity(): NetworkChangeActivity
    @ContributesAndroidInjector(modules = [ExampleActivityModule::class])
    abstract fun exampleActivity(): ExampleActivity

    @ContributesAndroidInjector abstract fun customFontViewsActivity(): CustomFontViewsActivity
    @ContributesAndroidInjector abstract fun waitActivity(): WaitActivity
    @ContributesAndroidInjector abstract fun rxLifecycleActivity(): RxLifecycleActivity
    @ContributesAndroidInjector abstract fun dataBindingActivity(): DataBindingActivity
}

@Module
abstract class ViewModelMapModule {
    @Binds @Singleton
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
abstract class ExampleActivityModule {
    @ContributesAndroidInjector abstract fun firstFragment(): FirstFragment
    @ContributesAndroidInjector abstract fun secondFragment(): SecondFragment
}

/**
 * Informs [ViewModelProvider] what [ViewModel] to produce for given class.
 * In bigger projects this can be slitted into multiple modules.
 */
@Module
class ViewModelProvidersModule {
    @Provides @IntoMap
    @ViewModelKey(ToolbarViewModel::class)
    fun toolbarViewModel(): ViewModel = ToolbarViewModel()

    @Provides @IntoMap
    @ViewModelKey(NetworkChangeViewModel::class)
    fun networkChangeViewModel(receiver: NetworkChangeReceiver): ViewModel =
            NetworkChangeViewModel(receiver)

    @Provides @IntoMap
    @ViewModelKey(CountingViewModel::class)
    fun countingViewModel(): ViewModel = CountingViewModel()

    @Provides @IntoMap
    @ViewModelKey(RxLifecycleViewModel::class)
    fun rxLifecycleViewModel(): ViewModel = RxLifecycleViewModel()

    @Provides @IntoMap
    @ViewModelKey(DataBindingViewModel::class)
    fun dataBindingViewModel(): ViewModel = DataBindingViewModel()
}