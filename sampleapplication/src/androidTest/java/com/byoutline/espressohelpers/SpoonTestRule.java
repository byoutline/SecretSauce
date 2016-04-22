package com.byoutline.espressohelpers;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import com.squareup.spoon.Spoon;
import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class SpoonTestRule<T extends Activity> extends ActivityTestRule<T> {

    public SpoonTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    public SpoonTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public SpoonTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        Espresso.setFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {
                Spoon.screenshot(SpoonTestRule.this.getActivity(), error.getClass().getSimpleName(), description.getClassName(), description.getMethodName());
                new DefaultFailureHandler(SpoonTestRule.this.getActivity()).handle(error, viewMatcher);
            }
        });
        return super.apply(base, description);
    }
}