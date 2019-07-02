package com.byoutline.sampleapplication

import androidx.annotation.ColorRes
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.byoutline.espressohelpers.ActivityRobot
import com.byoutline.espressohelpers.activityTestRule
import com.byoutline.espressohelpers.withRobot
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import org.junit.Rule
import org.junit.Test

class ContextDependenciesActivityShould {
    @get:Rule val rule = activityTestRule(ContextDependenciesActivity::class, ContextDependenciesActivityRobot())

    @Test
    fun setChangeBGColorDependingOnTheme() {
        rule.withRobot {
            verifyThatTVHasBackgroundColor(R.color.contextDependencyYellow)
            navigateToActivityWithDifferentTheme()
            verifyThatTVHasBackgroundColor(R.color.contextDependencyBlue)
        }
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

    fun navigateToActivityWithDifferentTheme() {
        clickOn(R.id.context_dependencies_btn)
    }
}
