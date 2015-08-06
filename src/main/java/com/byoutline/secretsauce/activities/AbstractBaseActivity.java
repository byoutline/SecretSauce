package com.byoutline.secretsauce.activities;

import android.app.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.events.ChangeWaitFragmentStateEvent;
import com.byoutline.secretsauce.events.EventSubscriber;
import com.byoutline.secretsauce.events.InternalDismissDialogsEvent;
import com.byoutline.secretsauce.fragments.DatePickerFragment;
import com.byoutline.secretsauce.fragments.WaitDialogFragment;
import com.byoutline.secretsauce.utils.LogUtils;
import com.byoutline.secretsauce.utils.ViewUtils;
import com.squareup.otto.Subscribe;


/**
 * Base activity with some common code for fragment manipulation.
 *
 * @deprecated As of AppCompat 22.1 replaced with {@link BaseAppCompatActivity} for
 * navigation drawer support and toolbar.
 */
@Deprecated
public abstract class AbstractBaseActivity extends Activity implements HostActivity {

    private static final String TAG = LogUtils.internalMakeLogTag(AbstractBaseActivity.class);
    protected FragmentManager mFragmentManager;
    private final DelayedWaitDialogDisplayer delayedWaitDialogDisplayer = new DelayedWaitDialogDisplayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Settings.DEBUG) {
            //calculate key hash for Facebook - display in logs
            ViewUtils.getKeyHash("com.byoutline.secretsauce", getApplicationContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Settings.BUS.register(eventSubscriber);
        SecretSauceModule.dialogsManager.registerAndPostEvents();
        changeWaitFragmentState(SecretSauceModule.waitDialogModel.shouldBeVisible());
    }

    @Override
    public void onPause() {
        delayedWaitDialogDisplayer.removeMessages();
        SecretSauceModule.dialogsManager.unregister();
        Settings.BUS.unregister(eventSubscriber);
        super.onPause();
    }

    /*
     * Action bar related methods
     */

    public void setUpActionbarFont() {
        ViewUtils.setUpActionbarFont(this, Settings.ACTIONBAR_FONT_NAME);
    }


    public void setDisplayHomeAsUpEnabled(boolean show) {
        getActionBar().setDisplayHomeAsUpEnabled(show);
    }

    /*
     * Software keyboard managment.
     */

    @Override
    public void hideKeyboard() {
        View focused = getCurrentFocus();
        if (focused != null) {
            Settings.INPUT_MANAGER.hideSoftInputFromWindow(focused.getWindowToken(), 0);
        }
    }

    @Override
    public void showKeyboard() {
        Settings.INPUT_MANAGER.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /*
     * Fragments and dialogs
     */


    @Override
    public void finishFragment() {
        getFragmentManager().popBackStack();
        hideKeyboard();
    }

    public void showFragment(Fragment fragment, boolean addToBackStack) {
        showFragmentWithReplacing(fragment, true, addToBackStack, null);
    }

    /**
     * @param fragment
     * @param replace
     * @param addToBackStack
     * @param animations     array which stores animation in order, enter, exit (2 elements) or
     *                       enter, exit, popEnter, popExit (4 elements) if argument in null default
     *                       animation is applied
     */

    public void showFragmentWithReplacing(Fragment fragment, boolean replace, boolean addToBackStack, int[] animations) {
        if (mFragmentManager == null) {
            mFragmentManager = getFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (animations != null) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setTransitionStyle(R.style.FragAnimation);
            if (animations.length == 2) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1]);
            } else if (animations.length == 4) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
            }
        }

        if (replace) {
            fragmentTransaction.replace(Settings.CONTAINER_VIEW_ID, fragment);
        } else {
            fragmentTransaction.add(Settings.CONTAINER_VIEW_ID, fragment);
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
    }

    @Override
    public void showDatePickerDialog(String dateString) {
        DatePickerFragment newFragment = new DatePickerFragment();

        if (!TextUtils.isEmpty(dateString)) {
            newFragment.setDateString(dateString);
        }

        showDialogFragment(newFragment, "datePicker");
    }

    @Override
    public void showDialogFragment(DialogFragment dialog, String tagUid) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(tagUid);
        if (prev != null) {
            ft.remove(prev);
        }

        SecretSauceModule.dialogsManager.addVisibleDialog(tagUid);
        dialog.show(ft, tagUid);
    }


    /**
     * dismisses dialog with given TAG.
     *
     * @param uid unique dialog id that is set as tag.
     * @return true if dialog was dismissed, false otherwise
     */
    public boolean dismissDialogWithUid(String uid) {
        SecretSauceModule.dialogsManager.removeVisibleDialog(uid);
        DialogFragment dialog = (DialogFragment) getFragmentManager().findFragmentByTag(uid);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (dialog != null && (dialog.isAdded())) {
            dialog.dismiss();
            return true;
        }
        return false;
    }

    protected void showWaitFragmentDelayed() {
        delayedWaitDialogDisplayer.showWaitFragmentDelayed();
    }

    protected void showWaitFragmentImmediately() {
        if (!SecretSauceModule.waitDialogModel.shouldBeVisible()) {
            return;
        }
        Fragment prev = getFragmentManager().findFragmentByTag(WaitDialogFragment.TAG);
        if (prev != null && prev.isVisible()) {
            return;
        }
        WaitDialogFragment dialog = new WaitDialogFragment();
        dialog.setCancelable(false);
        LogUtils.LOGV("WAIT", "showWaitFragmentImmediately");
        showDialogFragment(dialog, WaitDialogFragment.TAG);
    }

    protected void hideWaitFragment() {
        delayedWaitDialogDisplayer.removeMessages();
    }

    private void refreshWaitFragment() {
        LogUtils.LOGV(TAG, "refreshWaitFragment " + SecretSauceModule.waitDialogModel.shouldBeVisible());
        if (SecretSauceModule.waitDialogModel.shouldBeVisible()) {
            showWaitFragmentImmediately();
        } else {
            hideWaitFragment();
        }
    }

    private void changeWaitFragmentState(boolean shouldBeVisible) {
        if (shouldBeVisible) {
            showWaitFragmentDelayed();
        } else {
            hideWaitFragment();
        }
    }

    private final EventSubscriber eventSubscriber = new EventSubscriber() {

        @Subscribe
        public void onChangeWaitFragmentState(ChangeWaitFragmentStateEvent event) {
            changeWaitFragmentState(event.shouldBeVisible);
        }

        @Subscribe
        public void onInternalDismissDialogs(InternalDismissDialogsEvent event) {
            for (String dialogUid : event.dialogUids) {
                dismissDialogWithUid(dialogUid);
            }
        }
    };

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
//                } else if (msg.what == WHAT_FOR_SHOW_REFRESH) {
//                    refreshWaitFragment();
                }
            }
        };

        public void showWaitFragmentDelayed() {
            handler.sendEmptyMessageDelayed(WHAT_FOR_SHOW_WAIT, WAIT_DIALOG_DELAY_MILLIS);
        }

//        public void refreshWaitFragmentDelayed() {
//            handler.sendEmptyMessageDelayed(WHAT_FOR_SHOW_REFRESH, WAIT_DIALOG_REFRESH_DELAY_MILLIS);
//        }

        public void removeMessages() {
            handler.removeMessages(WHAT_FOR_SHOW_WAIT);
        }
    }
}


