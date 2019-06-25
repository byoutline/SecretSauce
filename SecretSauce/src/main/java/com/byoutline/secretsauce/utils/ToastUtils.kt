package com.byoutline.secretsauce.utils

import android.content.Context
import androidx.annotation.StringRes
import com.byoutline.secretsauce.SecretSauceSettings
import com.byoutline.secretsauce.utils.internal.ToastDisplayer

/**
 * Use this method to show Toast only in Debug apk, thanks to it you wont have to
 * delete all occurrences of Toasts before release :)
 */
fun Context.showDebugToast(text: String) {
    if (SecretSauceSettings.DEBUG) {
        showToast("DEBUG:\n" + text)
    }
}

fun Context.showToast(text: String, length: Int, cancelPrev: Boolean) {
    ToastDisplayer.showToast(this, text, length, cancelPrev)
}

fun Context.showToast(text: String) {
    ToastDisplayer.showToast(this, text)
}

fun Context.showToast(text: String, cancelPrev: Boolean) {
    ToastDisplayer.showToast(this, text, cancelPrev)
}

fun Context.showLongToast(text: String) {
    ToastDisplayer.showLongToast(this, text)
}

fun Context.showLongToast(@StringRes resId: Int) {
    ToastDisplayer.showLongToast(this, resId)
}

fun Context.showToast(@StringRes resId: Int) {
    ToastDisplayer.showToast(this, resId)
}

fun Context.showToast(@StringRes resId: Int, cancelPrev: Boolean) {
    ToastDisplayer.showToast(this, resId, cancelPrev)
}