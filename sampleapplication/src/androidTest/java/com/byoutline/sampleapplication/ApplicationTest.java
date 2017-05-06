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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class ApplicationTest {
    @Rule
    public final ActivityTestRule<MainActivity> main = new SpoonTestRule<>(MainActivity.class);

    @Test
    public void allButtonsShouldBeDisplayedTest() {
        //CHECK FIRST
        onView(withId(R.id.baseAppCompatActivityButton)).check(matches(isDisplayed()));
        //CHECK LAST
        onView((withId(R.id.dialogExample))).perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void datePickerShouldShowToTheUserTest() {
        //when
        onView((withId(R.id.datePickerExample))).perform(scrollTo(), click());
        onView((withId(R.id.dateBtn))).perform(click());
        //then
        onView((withId(android.R.id.button2)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void textViewShouldShowDateAfterSelectionDateTest() {
        //given
        CharSequence dateFormat = android.text.format.DateFormat.format("dd/MM/yyyy", new Date());

        //when
        onView((withId(R.id.datePickerExample))).perform(scrollTo(), click());
        onView((withId(R.id.dateBtn))).perform(click());
        onView((withId(android.R.id.button1)))
                .check(matches(isDisplayed())).perform(click());

        //then
        onView(withText(dateFormat.toString())).check(matches(isDisplayed()));
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