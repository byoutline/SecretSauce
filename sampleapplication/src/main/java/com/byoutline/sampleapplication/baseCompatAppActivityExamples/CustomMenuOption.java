package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import android.support.v4.app.Fragment;

import com.byoutline.secretsauce.fragments.MenuOption;

/**
 * Created by michalp on 12.04.16.
 */
public class CustomMenuOption implements MenuOption {
    private final String title;
    private final Class<? extends Fragment> fragmentclass;

    public CustomMenuOption(String optionTitle, Class<? extends Fragment> fragmentclass) {
        this.fragmentclass = fragmentclass;
        title = optionTitle;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Class getFragmentClass() {
        return fragmentclass;
    }
}
