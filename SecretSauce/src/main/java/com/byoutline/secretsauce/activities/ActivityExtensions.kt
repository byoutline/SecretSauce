package com.byoutline.secretsauce.activities

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.view.inputmethod.InputMethodManager
import com.byoutline.secretsauce.R
import com.byoutline.secretsauce.SecretSauceSettings

fun FragmentActivity.hideKeyboard() {
    currentFocus?.let { focused ->
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                ?.hideSoftInputFromWindow(focused.windowToken, 0)
    }
}

fun FragmentActivity.showKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun FragmentActivity.finishFragment() {
    supportFragmentManager.popBackStack()
    hideKeyboard()
}

/**
 * Adds fragment to the container.
 * [containerViewId] is required unless it was set globally via [SecretSauceSettings.set].
 * @param animations array which stores animation in order, enter, exit (2 elements) or
 *                   enter, exit, popEnter, popExit (4 elements) if argument in null default
 *                   animation is applied
 */
@SuppressLint("ResourceType")
fun FragmentActivity.showFragment(
        fragment: Fragment, addToBackStack: Boolean, replace: Boolean = true,
        animations: Array<Int>? = null,
        @IdRes containerViewId: Int = SecretSauceSettings.getContainerViewId()
) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    if (animations != null) {
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.setTransitionStyle(R.style.FragAnimation)
        if (animations.size == 2) {
            fragmentTransaction.setCustomAnimations(animations[0], animations[1])
        } else if (animations.size == 4) {
            fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3])
        }
    }

    if (replace) {
        fragmentTransaction.replace(containerViewId, fragment)
    } else {
        fragmentTransaction.add(containerViewId, fragment)
    }

    if (addToBackStack) {
        fragmentTransaction.addToBackStack(null)
    }
    fragmentTransaction.commit()
    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
}

/**
 * Shows or replaces [DialogFragment] with given [tagUid]
 */
fun FragmentActivity.showDialogFragment(dialog: DialogFragment, tagUid: String) {
    val ft = supportFragmentManager.beginTransaction()
    val prev = supportFragmentManager.findFragmentByTag(tagUid)
    if (prev != null) {
        ft.remove(prev)
    }

    dialog.show(ft, tagUid)
}

/**
 * dismisses dialog with given TAG.
 *
 * @param uid unique dialog id that is set as tag.
 * @return true if dialog was dismissed, false otherwise
 */
fun FragmentActivity.dismissDialogWithUid(uid: String): Boolean {
    val dialog = supportFragmentManager.findFragmentByTag(uid) as? DialogFragment

    val ft = supportFragmentManager.beginTransaction()
    if (dialog != null && dialog.isAdded) {
        dialog.dismiss()
        return true
    }
    ft.commitAllowingStateLoss()
    return false
}

fun FragmentActivity.clearBackStack() {
    val fragmentManager = supportFragmentManager
    while (fragmentManager.backStackEntryCount > 0) {
        fragmentManager.popBackStackImmediate()
    }
}