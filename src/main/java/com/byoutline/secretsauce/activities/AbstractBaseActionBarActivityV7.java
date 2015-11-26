package com.byoutline.secretsauce.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.events.ChangeWaitFragmentStateEvent;
import com.byoutline.secretsauce.events.EventSubscriber;
import com.byoutline.secretsauce.events.InternalDismissDialogsEvent;
import com.byoutline.secretsauce.fragments.DatePickerFragment;
import com.byoutline.secretsauce.fragments.NavigationDrawerFragment;
import com.byoutline.secretsauce.fragments.WaitDialogFragment;
import com.byoutline.secretsauce.utils.LogUtils;
import com.byoutline.secretsauce.utils.ViewUtils;
import com.squareup.otto.Subscribe;

import javax.annotation.Nullable;


/**
 * Base activity with some common code for fragment manipulation.
 *
 * @deprecated As of AppCompat 22.1 replaced with {@link BaseAppCompatActivity}
 */
@Deprecated
public abstract class AbstractBaseActionBarActivityV7 extends ActionBarActivity
        implements HostActivityV4, NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = LogUtils.internalMakeLogTag(AbstractBaseActionBarActivityV7.class);
    protected FragmentManager mSupportFragmentManager;
    protected android.app.FragmentManager mFragmentManager;
    @Nullable
    protected NavigationDrawerFragment navigationDrawerFragment;

    private final DelayedWaitDialogDisplayer delayedWaitDialogDisplayer = new DelayedWaitDialogDisplayer();

    TextView toolbarTitle;
    protected Toolbar toolbar;

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

    /**
     * Optional drawer
     */
    /**
     * Before setting drawer remember to call {@link #injectViewsAndSetUpToolbar(int, int)}.
     *
     * @param navigationDrawerId
     * @param drawerLayoutId
     */
    protected void setUpDrawer(@IdRes int navigationDrawerId, @IdRes int drawerLayoutId, boolean showingLearnDrawerEnabled) {
        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(navigationDrawerId);
        navigationDrawerFragment.setShowingLearnDrawerEnabled(showingLearnDrawerEnabled);
        navigationDrawerFragment.setUp(this,
                navigationDrawerId,
                (DrawerLayout) findViewById(drawerLayoutId),
                toolbar);
    }

    /**
     * {@link #setUpDrawer(int, int, boolean)} must be called before calling this method.
     *
     * @param show
     */
    public void setDrawerIndicatorEnabled(boolean show) {
        navigationDrawerFragment.setDrawerIndicatorEnabled(show);
    }

    /*
     * Action bar related methods
     */

    @Deprecated
    /**
     * @deprecated Use toolbar instead of action bar.
     */
    public void setUpActionbarFont() {
        ViewUtils.setUpActionbarFont(this, Settings.ACTIONBAR_FONT_NAME);
    }


    @Deprecated
    /**
     * @deprecated Use toolbar instead of action bar.
     */
    public void setDisplayHomeAsUpEnabled(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    /*
     * Toolbar
     */
    protected void injectViewsAndSetUpToolbar(@IdRes int toolbarLayoutId, @IdRes int toolbarTitleId) {
        toolbar = (Toolbar) findViewById(toolbarLayoutId);
        toolbarTitle = (TextView) findViewById(toolbarTitleId);
        setSupportActionBar(toolbar);
        ViewCompat.setElevation(toolbar, ViewUtils.convertDpToPixel(4, this));
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        setToolbarText(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        setToolbarText(title);
    }


    public void setToolbarText(CharSequence text) {
        super.setTitle("");
        toolbarTitle.setText(text);
    }

    public void setToolbarText(@StringRes int textId) {
        setToolbarText(getString(textId));
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
        getSupportFragmentManager().popBackStack();
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
    @SuppressLint("ResourceType")
    public void showFragmentWithReplacing(Fragment fragment, boolean replace, boolean addToBackStack, int[] animations) {
        if (mSupportFragmentManager == null) {
            mSupportFragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
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

    public void showFragment(android.app.Fragment fragment, boolean addToBackStack) {
        showFragmentWithReplacing(fragment, true, addToBackStack, null);
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment != null && navigationDrawerFragment.isDrawerOpen()) {
            navigationDrawerFragment.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    /**
     * @param fragment
     * @param replace
     * @param addToBackStack
     * @param animations     array which stores animation in order, enter, exit (2 elements) or
     *                       enter, exit, popEnter, popExit (4 elements) if argument in null default
     *                       animation is applied
     */

    public void showFragmentWithReplacing(android.app.Fragment fragment, boolean replace, boolean addToBackStack, int[] animations) {

        if (mFragmentManager == null) {
            mFragmentManager = getFragmentManager();
        }
        android.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (animations != null) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setTransitionStyle(com.byoutline.secretsauce.R.style.FragAnimation);
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

    public void showDialogFragment(DialogFragment dialog, String tagUid) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tagUid);
        if (prev != null) {
            ft.remove(prev);
        }

        SecretSauceModule.dialogsManager.addVisibleDialog(tagUid);
        dialog.show(ft, tagUid);
    }

    public void showDialogFragment(android.app.DialogFragment dialog, String tagUid) {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag(tagUid);
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
        DialogFragment dialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag(uid);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
        Fragment prev = getSupportFragmentManager().findFragmentByTag(WaitDialogFragment.TAG);
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


