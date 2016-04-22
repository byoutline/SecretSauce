package com.byoutline.sampleapplication.baseappcompatactivity;

import android.support.v4.app.Fragment;

import com.byoutline.secretsauce.fragments.MenuOption;

/**
 * Storage class menu item data.
 */
public class CustomMenuOption implements MenuOption {
    private final String title;
    private final Class<? extends Fragment> fragmentClass;

    public CustomMenuOption(String optionTitle, Class<? extends Fragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
        title = optionTitle;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Class getFragmentClass() {
        return fragmentClass;
    }
}
