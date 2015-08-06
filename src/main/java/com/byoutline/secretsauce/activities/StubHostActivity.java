package com.byoutline.secretsauce.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import com.byoutline.secretsauce.utils.LogUtils;

public class StubHostActivity implements HostActivity {
    private static final String TAG = LogUtils.internalMakeLogTag(StubHostActivity.class);

    @Override
    public void finishFragment() {
        LogUtils.LOGE(TAG, "Stub called for: finishFragment");
    }

    @Override
    public void hideKeyboard() {
        LogUtils.LOGE(TAG, "Stub called for: hideKeyboard");
    }

    @Override
    public void showKeyboard() {
        LogUtils.LOGE(TAG, "Stub called for: showKeyboard");
    }

    @Override
    public void showDialogFragment(DialogFragment instance, String tag) {
        LogUtils.LOGE(TAG, "Stub called for: showDialogFragment");
    }

    @Override
    public void showFragment(Fragment fragment, boolean addToBackStack) {
        LogUtils.LOGE(TAG, "Stub called for: showFragment");
    }

    @Override
    public void showFragmentWithReplacing(Fragment fragment, boolean replace, boolean addToBackStack, int[] animations) {
        LogUtils.LOGE(TAG, "Stub called for: showFragmentWithReplacing");
    }

    @Override
    public void showDatePickerDialog(String dateString) {
        LogUtils.LOGE(TAG, "Stub called for: showDatePickerDialog");
    }
}