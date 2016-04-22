package com.byoutline.sampleapplication.baseappcompatactivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;

import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.fragments.MenuOption;
import com.byoutline.secretsauce.fragments.NavigationDrawerFragment;

public class BaseAppCompatExampleActivity extends AbstractCustomActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_app_compat_example);
        injectViewsAndSetUpToolbar();
        setUpDrawer(true);
        setTitle(R.string.baseappcompat_title);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public Class<? extends Fragment> onNavigationDrawerItemSelected(MenuOption position) {
        Class<? extends Fragment> fragmentClass = position.getFragmentClass();
        try {
            if (fragmentClass != null) {
                Fragment fragment = (Fragment) position.getFragmentClass().newInstance();

                showFragment(fragment, false);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e("Example", "Failed to create fragment from drawer" + e.getMessage());
        }

        return fragmentClass;
    }
}
