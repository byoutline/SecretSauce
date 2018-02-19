package com.byoutline.sampleapplication.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.byoutline.sampleapplication.MainActivity
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity2
import com.byoutline.sampleapplication.contextdependencies.ContextModuleDependenciesActivity
import com.byoutline.sampleapplication.contextdependencies.ContextModuleDependenciesActivity2
import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity
import com.byoutline.sampleapplication.databinding.DataBindingActivity
import com.byoutline.sampleapplication.databinding.DataBindingViewModel
import com.byoutline.sampleapplication.draweractivity.*

import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.rx.RxLifecycleViewModel
import com.byoutline.sampleapplication.waitlayout.WaitActivity
import com.byoutline.secretsauce.di.ViewModelFactory
import com.byoutline.secretsauce.di.ViewModelKey
import com.byoutline.secretsauce.utils.NetworkChangeViewModel
import dagger.Binds
import dagger.Module
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

    @ContributesAndroidInjector(modules = [ContextModuleDependenciesActivity::class])
    abstract fun contextDependenciesActivity(): ContextDependenciesActivity

    @ContributesAndroidInjector(modules = [ContextModuleDependenciesActivity2::class])
    abstract fun contextDependenciesActivity2(): ContextDependenciesActivity2
}

@Module
abstract class ExampleActivityModule {
    @ContributesAndroidInjector abstract fun firstFragment(): FirstFragment
    @ContributesAndroidInjector abstract fun secondFragment(): SecondFragment
}

/**
 * Informs [ViewModelProvider] what [ViewModel] to produce for given class.
 * In bigger projects this can be split into multiple modules.
 * [ViewModelFactory] is a class that will have map of viewModel class to its creator.
 */
@Module(includes = [NetworkActivityModule::class])
abstract class ViewModelMapModule {
    @Binds @Singleton
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap
    @ViewModelKey(ToolbarViewModel::class)
    abstract fun toolbarViewModel(model: ToolbarViewModel): ViewModel

    @Binds @IntoMap
    @ViewModelKey(NetworkChangeViewModel::class)
    abstract fun networkChangeViewModel(model: NetworkChangeViewModel): ViewModel

    @Binds @IntoMap
    @ViewModelKey(CountingViewModel::class)
    abstract fun countingViewModel(model: CountingViewModel): ViewModel

    @Binds @IntoMap
    @ViewModelKey(RxLifecycleViewModel::class)
    abstract fun rxLifecycleViewModel(model: RxLifecycleViewModel): ViewModel

    @Binds @IntoMap
    @ViewModelKey(DataBindingViewModel::class)
    abstract fun dataBindingViewModel(model: DataBindingViewModel): ViewModel
}
