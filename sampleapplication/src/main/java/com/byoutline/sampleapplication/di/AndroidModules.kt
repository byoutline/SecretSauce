package com.byoutline.sampleapplication.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.byoutline.sampleapplication.MainActivity
import com.byoutline.sampleapplication.MainActivityVM
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity2
import com.byoutline.sampleapplication.contextdependencies.ContextModuleDependenciesActivity2Module
import com.byoutline.sampleapplication.contextdependencies.ContextModuleDependenciesActivityModule
import com.byoutline.sampleapplication.daggerexample.AndroidLibSubmoduleDaggerActivity
import com.byoutline.sampleapplication.daggerexample.DaggerExampleFragmentModule
import com.byoutline.sampleapplication.daggerexample.DaggerExampleMainActivityModule
import com.byoutline.sampleapplication.databinding.DataBindingActivity
import com.byoutline.sampleapplication.databinding.DataBindingViewModel
import com.byoutline.sampleapplication.draweractivity.*
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.rx.RxLifecycleVM
import com.byoutline.sampleapplication.waitlayout.WaitActivity
import com.byoutline.secretsauce.di.ViewModelFactory
import com.byoutline.secretsauce.di.ViewModelKey
import com.byoutline.secretsauce.utils.NetworkChangeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton


/**
 * Register activities with Android Dagger.
 *
 * Since sometimes there is more then one path to activity and because we want to keep hierarchy relatively flat
 * we declare all activities in this module directly.
 *
 * Do note that if you are only ever using [ViewModel] injected via [ViewModelFactory]/[com.byoutline.secretsauce.lifecycle.getViewModel]
 * without any Context dependencies you don't need that class since it is separate mechanism.
 */
@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector abstract fun mainActivity(): MainActivity
    @ContributesAndroidInjector abstract fun networkChangeActivity(): NetworkChangeActivity
    // This Activity has 2 fragments, we declare them for Dagger by passing them to @ContributesAndroidInjector
    @ContributesAndroidInjector(modules = [ExampleActivityModule::class])
    abstract fun exampleActivity(): DrawerActivity

    // If activity such as CustomFontViewsActivity does not use Dagger Android (neither itself or in child fragments)
    // we can skip declaring it.
    // @ContributesAndroidInjector abstract fun customFontViewsActivity(): CustomFontViewsActivity
    // But we can also declare such an activities if we expect that it may need Dagger Android in the future
    @ContributesAndroidInjector abstract fun waitActivity(): WaitActivity

    @ContributesAndroidInjector abstract fun rxLifecycleActivity(): RxLifecycleActivity
    @ContributesAndroidInjector abstract fun dataBindingActivity(): DataBindingActivity

    // This Activity has dependencies that use Activity Context - We inform Dagger that
    // in scope of this activity it can use ContextDependenciesActivity as Context
    @ContributesAndroidInjector(modules = [ContextModuleDependenciesActivityModule::class])
    abstract fun contextDependenciesActivity(): ContextDependenciesActivity

    @ContributesAndroidInjector(modules = [ContextModuleDependenciesActivity2Module::class])
    abstract fun contextDependenciesActivity2(): ContextDependenciesActivity2

    // This Activity comes from different Gradle Module, but can also be declared here.
    // It contains both fragment and Context dependencies.
    @ContributesAndroidInjector(modules = [DaggerExampleMainActivityModule::class])
    abstract fun daggerExampleMainActivity(): AndroidLibSubmoduleDaggerActivity
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
 * We are using [ViewModelFactory] scoping to keep [ViewModel] lifecycle bound either to
 * Activity of Fragment lifecycle we skip any extra [javax.inject.Scope] annotation. That scope
 * is usually good enough, and flat hierarchy of [ViewModel]s is significantly simpler then the nested version.
 * The main downside is that if you inject [ViewModel] to fe: Fragment and Activity they will get 2 separate instances.
 * Because of that it is suggested to pull [ViewModel] via interface Callbacks instead of Injecting it multiple times.
 */
@Module(includes = [NetworkActivityModule::class, DaggerExampleFragmentModule::class])
abstract class ViewModelMapModule {
    @Binds @Singleton
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds @IntoMap
    @ViewModelKey(MainActivityVM::class)
    abstract fun mainActivityVM(model: MainActivityVM): ViewModel

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
    @ViewModelKey(RxLifecycleVM::class)
    abstract fun rxLifecycleViewModel(model: RxLifecycleVM): ViewModel

    @Binds @IntoMap
    @ViewModelKey(DataBindingViewModel::class)
    abstract fun dataBindingViewModel(model: DataBindingViewModel): ViewModel
}
