package com.byoutline.espressohelpers

import android.Manifest
import android.app.Activity
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import com.byoutline.sampleapplication.MainActivityItem
import com.byoutline.sampleapplication.MainActivityItems
import com.byoutline.sampleapplication.di.DependenciesThatNeedOverwriteInTestsModule
import com.squareup.spoon.SpoonRule
import dagger.Module
import dagger.Provides

inline fun <reified T : Activity> activityTestRule(
        examples: List<MainActivityItem>? = null,
        launchActivity: Boolean = true,
        robot: ActivityRobot? = null
): ActivityTestRule<T> {
    val permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)
    val spoonRule = SpoonRule()
    val rule = DaggerActivityTestRule(
            activityClass = T::class.java,
            dependenciesOverriddenForTestsModule = DependenciesOverriddenForTestsModule(examples),
            extraRules = arrayOf(permissionRule, spoonRule),
            launchActivity = launchActivity
    )
    if (robot != null) {
        robot.rule = rule
        robot.spoonRule = spoonRule
    }
    return rule
}

@Module
class DependenciesOverriddenForTestsModule(
        private val examples: List<MainActivityItem>?,
        private val defaults: DependenciesThatNeedOverwriteInTestsModule = DependenciesThatNeedOverwriteInTestsModule()
) {
    @Provides @MainActivityItems
    fun examples() = examples ?: defaults.examples()
}

abstract class ActivityRobot {
    lateinit var rule: ActivityTestRule<*>
    lateinit var spoonRule: SpoonRule
    fun takeScreenshot(tag: String) {
        spoonRule.screenshot(rule.activity, tag)
    }
}