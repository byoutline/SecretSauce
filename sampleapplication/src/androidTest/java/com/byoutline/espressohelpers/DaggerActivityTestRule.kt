package com.byoutline.espressohelpers

import android.Manifest
import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import com.byoutline.sampleapplication.MainActivityItem
import com.byoutline.sampleapplication.SampleApp
import com.byoutline.sampleapplication.dioverrides.DaggerAppComponentTest
import com.byoutline.sampleapplication.dioverrides.DependenciesOverriddenForTestsModule
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.squareup.spoon.SpoonRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.reflect.KClass

class DaggerActivityTestRule<ACT : Activity, out ROBOT : ActivityRobot?>(
    activityClass: Class<ACT>,
    initialTouchMode: Boolean = true,
    launchActivity: Boolean = true,
    private val dependenciesOverriddenForTestsModule: DependenciesOverriddenForTestsModule,
    val robot: ROBOT,
    private val extraRules: Array<TestRule> = emptyArray()
) : ActivityTestRule<ACT>(activityClass, initialTouchMode, launchActivity) {

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

inline fun <reified ACT : Activity, reified ROBOT : ActivityRobot> activityTestRule(
    activityClass: KClass<ACT>,
    robot: ROBOT,
    examples: List<MainActivityItem>? = null,
    launchActivity: Boolean = true
): DaggerActivityTestRule<ACT, ROBOT> {
    // An alternative for declaring permissions in debug manifest. In this case this is redundant since
    // we already requested those permission via Debug Manifest, but it can be useful if either
    // * you don't want to create Debug Manifest
    // * you want to get permissions only in some cases
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val spoonRule = SpoonRule()
    val rule = DaggerActivityTestRule(
        activityClass = activityClass.java,
        launchActivity = launchActivity,
        dependenciesOverriddenForTestsModule = DependenciesOverriddenForTestsModule(examples),
        robot = robot,
        extraRules = arrayOf(permissionRule, spoonRule)
    )
    robot.rule = rule
    robot.spoonRule = spoonRule
    return rule
}

inline fun <reified ACT : Activity> activityTestRule(
    examples: List<MainActivityItem>? = null,
    launchActivity: Boolean = true
): ActivityTestRule<ACT> {
    return DaggerActivityTestRule(
        activityClass = ACT::class.java,
        launchActivity = launchActivity,
        dependenciesOverriddenForTestsModule = DependenciesOverriddenForTestsModule(examples),
        robot = null
    )
}

fun <ACT : Activity, ROBOT : ActivityRobot> DaggerActivityTestRule<ACT, ROBOT>.withRobot(block: ROBOT.() -> Unit) =
    robot.apply(block)