package com.byoutline.secretsauce.activities;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.dagger.SecretSauceModule;
import com.byoutline.secretsauce.fragments.DatePickerFragment;
import com.byoutline.secretsauce.fragments.NavigationDrawerFragment;
import com.byoutline.secretsauce.utils.ViewUtils;

import javax.annotation.Nullable;


/**
 * Base activity with some common code for fragment manipulation.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity
        implements HostActivityV4 {

    protected FragmentManager mSupportFragmentManager;
    @Nullable
    protected NavigationDrawerFragment navigationDrawerFragment;
    protected Toolbar toolbar;
    TextView toolbarTitle;

    /*
     * Optional drawer
     */

    /**
     * Before setting drawer remember to call {@link #injectViewsAndSetUpToolbar(int, int)}.
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
     */
    public void setDrawerIndicatorEnabled(boolean show) {
        navigationDrawerFragment.setDrawerIndicatorEnabled(show);
    }

    /*
     * Toolbar
     */
    protected void injectViewsAndSetUpToolbar(@IdRes int toolbarLayoutId, @IdRes int toolbarTitleId) {
        toolbar = findViewById(toolbarLayoutId);
        toolbarTitle = findViewById(toolbarTitleId);
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
     * Software keyboard management.
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

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment != null && navigationDrawerFragment.isDrawerOpen()) {
            navigationDrawerFragment.closeDrawers();
            return;
        }
        super.onBackPressed();
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
        DialogFragment dialog = (DialogFragment) mSupportFragmentManager.findFragmentByTag(uid);

        FragmentTransaction ft = mSupportFragmentManager.beginTransaction();
        if (dialog != null && (dialog.isAdded())) {
            dialog.dismiss();
            return true;
        }
        ft.commitAllowingStateLoss();
        return false;
    }
}


