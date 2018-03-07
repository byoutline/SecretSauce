package com.byoutline.espressohelpers

import com.byoutline.sampleapplication.di.ActivitiesModule
import com.byoutline.sampleapplication.di.AppComponent
import com.byoutline.sampleapplication.di.ViewModelMapModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ActivitiesModule::class,
    ViewModelMapModule::class,
    AndroidInjectionModule::class,
    DependenciesOverriddenForTestsModule::class
])
interface AppComponentTest : AppComponent

