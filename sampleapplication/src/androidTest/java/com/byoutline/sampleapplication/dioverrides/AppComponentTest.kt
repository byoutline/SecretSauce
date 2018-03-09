package com.byoutline.sampleapplication.dioverrides

import com.byoutline.sampleapplication.di.ActivitiesModule
import com.byoutline.sampleapplication.di.AppComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    // Activities structure is the same in tests
    ActivitiesModule::class,
    // Android Injection from dagger.android can remain the same
    AndroidInjectionModule::class,
    // If you want to override viewModels - fe: for taking screenshots of screens you need to
    // specify replacement module that knows how to create testView models for tested activities/fragments
    // This is example of replacing abstract module that uses @Binds
    ViewModelMapTestModule::class,
    // We replace DependenciesThatNeedOverwriteInTestsModule with our own implementation.
    // This is example of replacing concrete module that uses @Provides
    DependenciesOverriddenForTestsModule::class
])
interface AppComponentTest : AppComponent

