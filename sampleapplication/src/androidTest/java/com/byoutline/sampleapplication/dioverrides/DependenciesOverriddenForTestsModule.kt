package com.byoutline.sampleapplication.dioverrides

import com.byoutline.sampleapplication.MainActivityItem
import com.byoutline.sampleapplication.MainActivityItems
import com.byoutline.sampleapplication.di.DependenciesThatNeedOverwriteInTestsModule
import dagger.Module
import dagger.Provides

@Module
class DependenciesOverriddenForTestsModule(
    private val examples: List<MainActivityItem>?,
    private val defaults: DependenciesThatNeedOverwriteInTestsModule = DependenciesThatNeedOverwriteInTestsModule()
) {
    @Provides @MainActivityItems
    fun examples() = examples ?: defaults.examples()
}

