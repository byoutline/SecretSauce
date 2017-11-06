package com.byoutline.secretsauce.utils.internal;

import android.content.Context;
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
     * @param ctx
     * @param text
     */
    public static void showDebugToast(Context ctx, String text) {
        if (Settings.INSTANCE.getDEBUG()) {
            showToast(ctx, "DEBUG:\n" + text);
        }
    }

    public static void showToast(final Context ctx, final String text, final int length, final boolean cancelPrev) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // on ui thread.
            showToastInner(ctx, text, length, cancelPrev);
        } else {
            Handler mainHandler = new Handler(ctx.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    showToastInner(ctx, text, length, cancelPrev);
                }
            });
        }
    }

    private static void showToastInner(Context ctx, String text, int length, boolean cancelPrev) {
        if (cancelPrev) {
            Toast toast = prevToastRef.get();
            if (toast != null) {
                toast.cancel();
            }
        }
        Toast toast = Toast.makeText(ctx, text, length);
        prevToastRef = new WeakReference<>(toast);
        toast.show();
    }

    public static void showToast(Context ctx, String text) {
        boolean cancelPrev = false;
        showToast(ctx, text, Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showToast(Context ctx, String text, boolean cancelPrev) {
        showToast(ctx, text, Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showLongToast(Context ctx, String text) {
        boolean cancelPrev = false;
        showToast(ctx, text, Toast.LENGTH_LONG, cancelPrev);
    }

    public static void showLongToast(Context ctx, int resId) {
        boolean cancelPrev = false;
        showToast(ctx, ctx.getString(resId), Toast.LENGTH_LONG, cancelPrev);
    }

    public static void showToast(Context ctx, int resId) {
        boolean cancelPrev = false;
        showToast(ctx, ctx.getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showToast(Context ctx, int resId, boolean cancelPrev) {
        showToast(ctx, ctx.getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }
}
