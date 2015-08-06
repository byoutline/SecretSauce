package com.byoutline.secretsauce.activities;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public interface HostActivityV4 {
    void finishFragment();

    void hideKeyboard();

    void showKeyboard();

    void showDialogFragment(DialogFragment instance, String tag);

    void showFragment(Fragment fragment, boolean addToBackStack);

    void showFragmentWithReplacing(Fragment fragment, boolean replace, boolean addToBackStack, int[] animations);

    void showDatePickerDialog(String dateString);
}