package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.activities.BaseAppCompatActivity;

/**
 * Created by michalp on 12.04.16.
 */
public class AbstractCustomActivity extends BaseAppCompatActivity {
    protected void injectViewsAndSetUpToolbar() {
        injectViewsAndSetUpToolbar(R.id.toolbar, R.id.toolbar_title_tv);
    }

    protected void setUpDrawer(boolean showingLearnDrawerEnabled) {
        setUpDrawer(R.id.navigation_drawer, R.id.drawer_layout, showingLearnDrawerEnabled);
    }
}
