package com.byoutline.secretsauce.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.events.ChangeWaitFragmentStateEvent;
import com.byoutline.secretsauce.utils.LogUtils;
import com.squareup.otto.Subscribe;


/**
 * Simple loading fragment.
 */
public class WaitDialogFragment extends DialogFragment {

    public static final String TAG = LogUtils.internalMakeLogTag(WaitDialogFragment.class);

    public WaitDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, Settings.STYLE_DIALOG_FULL_SCREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(Settings.WAIT_DIALOG_LAYOUT, container, false);

        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Settings.BUS.register(this);
        changeWaitFragmentState(SecretSauceModule.waitDialogModel.shouldBeVisible());
    }

    @Override
    public void onPause() {
        Settings.BUS.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onEventMainThread(ChangeWaitFragmentStateEvent event) {
        changeWaitFragmentState(event.shouldBeVisible);
    }

    private void changeWaitFragmentState(boolean shouldBeVisible) {
        if (!shouldBeVisible) {
            LogUtils.LOGV("WAIT", "dissmised on wait dialog");
            dismiss();
        }
    }
}
