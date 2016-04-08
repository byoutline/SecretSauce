package com.byoutline.sampleapplication.baseCompatAppActivityExamples;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by michalp on 12.04.16.
 */
public abstract class AbstractCustomFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        setActionbar();
    }

    public boolean showOrderSpinner() {
        return false;
    }

    private void setActionbar() {
        AbstractCustomActivity baseActivity = getBaseActivity();
        if (baseActivity != null) {

            baseActivity.setDrawerIndicatorEnabled(!showBackButtonInActionbar());
            if (!TextUtils.isEmpty(getFragmentActionbarName())) {
                baseActivity.setTitle(getFragmentActionbarName());
            }

        }
    }

    public abstract String getFragmentActionbarName();

    public abstract boolean showBackButtonInActionbar();

    public AbstractCustomActivity getBaseActivity() {
        return (AbstractCustomActivity) getActivity();
    }

    public void invalidateMenu() {
        if (getBaseActivity() != null) {
            getBaseActivity().invalidateOptionsMenu();
        }
    }

    /**
     * This method allows you to finish you fragment similar to finish method in Activity.
     */
    public void finish() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


    public void setAutoKeyboardHideOnEdittextOutTouch(final View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    getBaseActivity().hideKeyboard();
                    return false;
                }
            });
        }
        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setAutoKeyboardHideOnEdittextOutTouch(innerView);
            }
        }
    }

    public void hideKeyboard() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideKeyboard();
        }
    }

    public void showKeyboard() {
        if (getBaseActivity() != null) {
            getBaseActivity().showKeyboard();
        }
    }
}
