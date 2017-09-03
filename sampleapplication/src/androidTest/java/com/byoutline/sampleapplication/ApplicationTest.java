package com.byoutline.sampleapplication;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import com.byoutline.espressohelpers.SpoonTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@LargeTest
public class ApplicationTest {
    @Rule
    public final ActivityTestRule<MainActivity> main = new SpoonTestRule<>(MainActivity.class);

    @Test
    public void allButtonsShouldBeDisplayedTest() {
        //CHECK FIRST
        onView(withId(R.id.baseAppCompatActivityButton)).check(matches(isDisplayed()));
        //CHECK LAST
        onView((withId(R.id.customViewsExample))).perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void fragmentShouldBeShownAfterClickDrawerOptionTest() {
        //when
        onView(withId(R.id.baseAppCompatActivityButton))
                .check(matches(isDisplayed())).perform(click());
        onView(withText("First Item")).perform(click());

        //then
        onView(withId(R.id.mainTV)).check(matches(isDisplayed()));
    }
}