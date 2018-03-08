package com.byoutline.sampleapplication

import android.support.annotation.ColorRes
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.byoutline.espressohelpers.ActivityRobot
import com.byoutline.espressohelpers.activityTestRule
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity2
import org.junit.Rule
import org.junit.Test


class ContextDependenciesActivityShould {
    val robot = ContextDependenciesActivityRobot()
    @get:Rule val rule = activityTestRule<ContextDependenciesActivity>(robot = robot)

    @Test
    fun setBackgroundColorFromTheme() {
        robot.verifyThatTVHasBackgroundColor(R.color.contextDependencyYellow)
    }
}

class ContextDependenciesActivity2Should {
    val robot = ContextDependenciesActivityRobot()
    @get:Rule val rule = activityTestRule<ContextDependenciesActivity2>(robot = robot)
    @Test
    fun setBackgroundColorFromTheme() {
        robot.verifyThatTVHasBackgroundColor(R.color.contextDependencyBlue)
    }
}


class ContextDependenciesActivityRobot : ActivityRobot() {
    fun verifyThatTVHasBackgroundColor(@ColorRes color: Int) {
        displayed {
            allOf {
                id(R.id.context_dependencies_tv)
                background(color)
            }
        }
        takeScreenshot("Theme_Background")
    }
}
