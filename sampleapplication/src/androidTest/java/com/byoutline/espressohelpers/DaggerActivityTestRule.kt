package com.byoutline.espressohelpers

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.byoutline.sampleapplication.SampleApp
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DaggerActivityTestRule<T : Activity>(
        activityClass: Class<T>,
        private val dependenciesOverriddenForTestsModule: DependenciesOverriddenForTestsModule,
        private val extraRules: Array<TestRule>,
        initialTouchMode: Boolean = true,
        launchActivity: Boolean = true
) : ActivityTestRule<T>(activityClass, initialTouchMode, launchActivity) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as SampleApp
        val component = DaggerAppComponentTest.builder()
                .networkActivityModule(NetworkActivityModule(app))
                .dependenciesOverriddenForTestsModule(dependenciesOverriddenForTestsModule)
                .build()
        app.setComponents(component)
    }

    override fun apply(base: Statement?, description: Description?): Statement {
        var result = super.apply(base, description)
        for (rule in extraRules) {
            result = rule.apply(result, description)
        }
        return result
    }
}


