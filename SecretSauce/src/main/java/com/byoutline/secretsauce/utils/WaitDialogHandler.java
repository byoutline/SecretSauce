package com.byoutline.secretsauce.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.activities.BaseAppCompatActivity;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.events.ChangeWaitFragmentStateEvent;
import com.byoutline.secretsauce.events.EventSubscriber;
import com.byoutline.secretsauce.events.InternalDismissDialogsEvent;
import com.byoutline.secretsauce.fragments.WaitDialogFragment;
import com.squareup.otto.Subscribe;

/**
 * Provides support for WaitFragment. To use create in activity and register
 * {@link #onResume()} and {@link #onPause()}
 *
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 * @deprecated Use {@link com.byoutline.secretsauce.views.WaitLayout} instead.
 */
@Deprecated
public class WaitDialogHandler {
    private final DelayedWaitDialogDisplayer delayedWaitDialogDisplayer = new DelayedWaitDialogDisplayer();
    private final BaseAppCompatActivity activity;
    final EventSubscriber eventSubscriber = new EventSubscriber() {

        @Subscribe
        public void onChangeWaitFragmentState(ChangeWaitFragmentStateEvent event) {
            changeWaitFragmentState(event.shouldBeVisible);
        }

        @Subscribe
        public void onInternalDismissDialogs(InternalDismissDialogsEvent event) {
            for (String dialogUid : event.dialogUids) {
                activity.dismissDialogWithUid(dialogUid);
            }
        }
    };
    private final FragmentManager supportFragmentManager;

    public WaitDialogHandler(BaseAppCompatActivity activity, FragmentManager supportFragmentManager) {
        this.activity = activity;
        this.supportFragmentManager = supportFragmentManager;
    }

    public void onResume() {
        Settings.BUS.register(eventSubscriber);
        SecretSauceModule.dialogsManager.registerAndPostEvents();
        changeWaitFragmentState(SecretSauceModule.waitDialogModel.shouldBeVisible());
    }

    public void onPause() {
        delayedWaitDialogDisplayer.removeMessages();
        SecretSauceModule.dialogsManager.unregister();
        Settings.BUS.unregister(eventSubscriber);
    }

    public void showWaitFragmentDelayed() {
        delayedWaitDialogDisplayer.showWaitFragmentDelayed();
    }

    public void showWaitFragmentImmediately() {
        if (!SecretSauceModule.waitDialogModel.shouldBeVisible()) {
            return;
        }
        Fragment prev = supportFragmentManager.findFragmentByTag(WaitDialogFragment.TAG);
        if (prev != null && prev.isVisible()) {
            return;
        }
        WaitDialogFragment dialog = new WaitDialogFragment();
        dialog.setCancelable(false);
        LogUtils.LOGV("WAIT", "showWaitFragmentImmediately");
        activity.showDialogFragment(dialog, WaitDialogFragment.TAG);
    }

    public void hideWaitFragment() {
        delayedWaitDialogDisplayer.removeMessages();
    }

    private void changeWaitFragmentState(boolean shouldBeVisible) {
        if (shouldBeVisible) {
            showWaitFragmentDelayed();
        } else {
            hideWaitFragment();
        }
    }

    class DelayedWaitDialogDisplayer {

        public static final int WHAT_FOR_SHOW_WAIT = 1;
        //        public static final int WHAT_FOR_SHOW_REFRESH = 2;
        public static final int WAIT_DIALOG_DELAY_MILLIS = 500;
//        public static final int WAIT_DIALOG_REFRESH_DELAY_MILLIS = 50;

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT_FOR_SHOW_WAIT) {
                    showWaitFragmentImmediately();
                }
            }
        };

        public void showWaitFragmentDelayed() {
            handler.sendEmptyMessageDelayed(WHAT_FOR_SHOW_WAIT, WAIT_DIALOG_DELAY_MILLIS);
        }

        public void removeMessages() {
            handler.removeMessages(WHAT_FOR_SHOW_WAIT);
        }
    }
}
