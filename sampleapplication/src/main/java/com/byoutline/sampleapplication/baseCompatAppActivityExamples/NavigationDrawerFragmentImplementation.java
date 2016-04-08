package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.fragments.MenuOption;
import com.byoutline.secretsauce.fragments.NavigationDrawerFragment;

public class NavigationDrawerFragmentImplementation extends NavigationDrawerFragment {


    public NavigationDrawerFragmentImplementation() {
        // Required empty public constructor
    }

    @Override
    protected int getNavigationDrawerFragmentLayoutId() {
        return R.layout.fragment_navigation_drawer;
    }

    @Override
    protected int getNavigationDrawerListId() {
        return R.id.drawer_lv;
    }

    @Override
    protected ArrayAdapter<MenuOption> getListAdapter(Activity activity) {
        ArrayAdapter adapter = new MenuAdapter(activity);
        return adapter;
    }
}
