package com.byoutline.secretsauce.fragments;

/**
 * Use <a href="https://developer.android.com/reference/android/support/design/widget/NavigationView.html">NavigationView</a>
 */
@Deprecated
public interface MenuOption {

    String getTitle();

    Class getFragmentClass();

}