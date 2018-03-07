package com.byoutline.espressohelpers

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.MainActivityItem
import com.byoutline.sampleapplication.MainActivityItems
import dagger.Module
import dagger.Provides

inline fun <reified T : Activity> activityTestRule(
        examples: List<MainActivityItem>? = null
): ActivityTestRule<T> = DaggerActivityTestRule(T::class.java, DependenciesOverriddenForTestsModule(examples))

@Module
class DependenciesOverriddenForTestsModule(
        private val examples: List<MainActivityItem>?
) {
    @Provides @MainActivityItems
    fun examples() = examples ?: listOf(MainActivityItem(AppCompatActivity::class.java, "0"))
}