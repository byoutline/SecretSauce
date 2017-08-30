package com.byoutline.secretsauce.utils.internal;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.byoutline.secretsauce.Settings;

import java.lang.ref.WeakReference;

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public final class ToastDisplayer {
    private static WeakReference<Toast> prevToastRef = new WeakReference<>(null);

    private ToastDisplayer() {
    }

    /**
     * Use this method to show Toast only in Debug apk, thanks to it you wont have to
     * delete all occurrences of Toasts before release :)
     *
     * @param text
     */
    public static void showDebugToast(String text) {
        if (Settings.INSTANCE.getDEBUG()) {
            showToast("DEBUG:\n" + text);
        }
    }

    public static void showToast(final String text, final int length, final boolean cancelPrev) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // on ui thread.
            showToastInner(text, length, cancelPrev);
        } else {
            Handler mainHandler = new Handler(Settings.INSTANCE.getCONTEXT().getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    showToastInner(text, length, cancelPrev);
                }
            });
        }
    }

    private static void showToastInner(String text, int length, boolean cancelPrev) {
        if (cancelPrev) {
            Toast toast = prevToastRef.get();
            if (toast != null) {
                toast.cancel();
            }
        }
        Toast toast = Toast.makeText(Settings.INSTANCE.getCONTEXT(), text, length);
        prevToastRef = new WeakReference<>(toast);
        toast.show();
    }

    public static void showToast(String text) {
        boolean cancelPrev = false;
        showToast(text, Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showToast(String text, boolean cancelPrev) {
        showToast(text, Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showLongToast(String text) {
        boolean cancelPrev = false;
        showToast(text, Toast.LENGTH_LONG, cancelPrev);
    }

    public static void showLongToast(int resId) {
        boolean cancelPrev = false;
        showToast(Settings.INSTANCE.getCONTEXT().getString(resId), Toast.LENGTH_LONG, cancelPrev);
    }

    public static void showToast(int resId) {
        boolean cancelPrev = false;
        showToast(Settings.INSTANCE.getCONTEXT().getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showToast(int resId, boolean cancelPrev) {
        showToast(Settings.INSTANCE.getCONTEXT().getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }
}
