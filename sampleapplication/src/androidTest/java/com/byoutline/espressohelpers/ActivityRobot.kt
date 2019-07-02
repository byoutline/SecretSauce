package com.byoutline.espressohelpers

import androidx.test.rule.ActivityTestRule
import com.jraska.falcon.Falcon
import com.squareup.spoon.SpoonRule

/** Robot that is aware of ActivityTestRule and knows how to takeScreenshots */
abstract class ActivityRobot {
    lateinit var rule: ActivityTestRule<*>
    lateinit var spoonRule: SpoonRule
    fun takeScreenshot(tag: String) {
        // Taking screenshots takes some time, do it only when generating spoon report.
        if (!takeScreenshots) return
        val file = spoonRule.screenshot(rule.activity, tag)
        Falcon.takeScreenshot(rule.activity, file)
    }

    companion object {
        var takeScreenshots: Boolean = false
    }
}