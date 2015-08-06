package com.byoutline.secretsauce.fragments;

public class MenuOption {

    private final String mTitle;
    public final Class mFragmentClass;

    public MenuOption(String title, Class fragmentClass) {
        mTitle = title;
        mFragmentClass = fragmentClass;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}