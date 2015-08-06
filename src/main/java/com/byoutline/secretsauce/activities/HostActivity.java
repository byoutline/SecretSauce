package com.byoutline.secretsauce.activities;

import android.app.DialogFragment;
import android.app.Fragment;

public interface HostActivity {
    void finishFragment();

    void hideKeyboard();

    void showKeyboard();

    void showDialogFragment(DialogFragment instance, String tag);

    void showFragment(Fragment fragment, boolean addToBackStack);

    void showFragmentWithReplacing(Fragment fragment, boolean replace, boolean addToBackStack, int[] animations);

    void showDatePickerDialog(String dateString);
}