package com.byoutline.sampleapplication

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.byoutline.espressohelpers.activityTestRule
import com.byoutline.sampleapplication.daggerexample.AndroidLibSubmoduleDaggerActivity
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test

class AndroidLibSubmoduleDaggerActivityShould {
    @get:Rule val rule = activityTestRule<AndroidLibSubmoduleDaggerActivity>()
    val robot = AndroidLibSubmoduleDaggerActivityRobot()

    @Test
    fun displayFragmentAfterBtnClick() {
        robot.apply {
            openFragment()
            verifyThatTextOfFragmentIsDisplayed()
        }
    }
}

class AndroidLibSubmoduleDaggerActivityRobot {
    fun openFragment() {
        click { id(R.id.dagger_example_show_fragment_btn) }
    }

    fun verifyThatTextOfFragmentIsDisplayed() {
        val textPrefix = "Fragment was attached "
        displayed {
            text(object : TypeSafeMatcher<String>() {
                override fun describeTo(description: Description) {
                    description.appendText("starts with '$textPrefix'")
                }

                override fun matchesSafely(item: String): Boolean =
                        item.startsWith(textPrefix)
            })
            text(R.string.fragment_label)
        }
    }
}

