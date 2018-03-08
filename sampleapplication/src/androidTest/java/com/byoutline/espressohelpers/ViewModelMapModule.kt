package com.byoutline.espressohelpers

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.byoutline.sampleapplication.MainActivityVM
import com.byoutline.sampleapplication.RxLifecycleVMTest
import com.byoutline.sampleapplication.daggerexample.DaggerExampleFragmentModule
import com.byoutline.sampleapplication.databinding.DataBindingViewModel
import com.byoutline.sampleapplication.draweractivity.CountingViewModel
import com.byoutline.sampleapplication.draweractivity.ToolbarViewModel
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.byoutline.sampleapplication.rx.RxLifecycleVM
import com.byoutline.secretsauce.di.ViewModelFactory
import com.byoutline.secretsauce.di.ViewModelKey
import com.byoutline.secretsauce.utils.NetworkChangeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [NetworkActivityModule::class, DaggerExampleFragmentModule::class])
abstract class ViewModelMapTestModule {
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
    abstract fun rxLifecycleViewModel(model: RxLifecycleVMTest): ViewModel

    @Binds @IntoMap
    @ViewModelKey(DataBindingViewModel::class)
    abstract fun dataBindingViewModel(model: DataBindingViewModel): ViewModel
}