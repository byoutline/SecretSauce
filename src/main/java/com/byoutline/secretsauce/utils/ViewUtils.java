package com.byoutline.secretsauce.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.widget.*;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.events.ShowValidationErrorsEvent;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ViewUtils {
    public static final int MSG_REQUEST_REFRESH_VIEW = 200;
    public static final int MSG_IMMEDIATE_REFRESH = 201;

    public static final int MSG_REQUEST_GALLERY_SET_MONTH = 300;
    public static final int MSG_IMMEDIATE_GALLERY_SET_DATE = 301;

    public static final int FILTER_DELAY_MILLIS = 500;         //TODO: Set experimental delay time
    public static final int MSG_REQUEST_CHANGE_STATE = 400;
    public static final int MSG_IMMEDIATE_CHANGE_STATE = 401;
    private static WeakReference<Toast> prevToastRef = new WeakReference<>(null);

    public static void showView(View view, boolean visibility) {
        if (view != null) {
            if (visibility) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static int dpToPx(float dp, Context context) {
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, int length) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        int min = Math.min(length, listAdapter.getCount());
        for (int i = 0; i < min; i++) {
            View listItem = listAdapter.getView(i, null, listView);

            listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
                MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.UNSPECIFIED));
            listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * Displays integer inside given pattern in TextView, or hides TextView if null given.
     */
    public static void setTextForViewOrHideIt(TextView textView, String value) {
        if (TextUtils.isEmpty(value)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(value);
        }
    }

    public static void removeFromLayout(RelativeLayout view) {
        if (view != null && view.getParent() != null) {
            ((ViewManager) view.getParent()).removeView(view);
        }
    }

    /**
     * Use this method to show Toast only in Debug apk, thanks to it you wont have to
     * delete all occurrences of Toasts before release :)
     *
     * @param text
     */
    public static void showDebugToast(String text) {
        if (Settings.DEBUG) {
            showToast("DEBUG:\n" + text);
        }
    }

    public static void showToast(final String text, final int length, final boolean cancelPrev) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // on ui thread.
            showToastInner(text, length, cancelPrev);
        } else {
            Handler mainHandler = new Handler(Settings.CONTEXT.getMainLooper());
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
        Toast toast = Toast.makeText(Settings.CONTEXT, text, length);
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
        showToast(Settings.CONTEXT.getString(resId), Toast.LENGTH_LONG, cancelPrev);
    }

    public static void showToast(int resId) {
        boolean cancelPrev = false;
        showToast(Settings.CONTEXT.getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void showToast(int resId, boolean cancelPrev) {
        showToast(Settings.CONTEXT.getString(resId), Toast.LENGTH_SHORT, cancelPrev);
    }

    public static void setText(TextView tv, String text) {
        if (tv != null && text != null) {
            tv.setText(text);
        }
    }

    public static void setTextOrClear(TextView tv, String text) {
        if (tv != null) {
            if (text == null) {
                tv.setText("");
            } else {
                tv.setText(text);
            }
        }
    }

    public static String getKeyHash(String pckgName, Context context) {
        // Add code to print out the key hash
        String keyHash = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(pckgName, PackageManager.GET_SIGNATURES);
            if (info.signatures == null) {
                // Test environment may return no signatures.
                return keyHash;
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHash);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {

        }
        return keyHash;
    }

    public static String validateEmptyEditText(EditText editText, String errorMessage) {
        String value = editText.getText().toString();
        if (TextUtils.isEmpty(value)) {
            editText.setError(errorMessage);
        }
        return value;
    }

    public static TextView setUpActionbarFont(Activity context, Typeface font) {
        int titleId = context.getResources().getIdentifier("action_bar_title", "id", "android");
        TextView actionbarTitleTv = (TextView) context.findViewById(titleId);
        if (actionbarTitleTv != null) {
            actionbarTitleTv.setTypeface(font);
        }
        return actionbarTitleTv;
    }

    public static void showValidationErrors(String screenName, ShowValidationErrorsEvent event, Map<String, View> errorViews) {
        if (event.screenName.equals(screenName)) {

            for (Map.Entry<String, String[]> errorEntry : event.validationErrors.entrySet()) {
                View viewWithError = errorViews.get(errorEntry.getKey());
                if (viewWithError != null && viewWithError instanceof TextView) {
                    StringBuilder errorsMultiline = new StringBuilder();
                    for (String error : errorEntry.getValue()) {
                        errorsMultiline.append(error);
                        errorsMultiline.append("\n");
                    }

                    viewWithError.requestFocus();
                    ((TextView) viewWithError).setError(errorsMultiline);

                }
            }
        }
    }

    public static TextView centerActionBarTitleAndSetFont(Activity activity, Typeface font) {
        TextView titleTextView = null;
        int titleId = activity.getResources().getIdentifier("action_bar_title", "id", "android");

        // Final check for non-zero invalid id
        if (titleId > 0) {
            titleTextView = (TextView) activity.findViewById(titleId);
            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
            // Fetch layout parameters of titleTextView (LinearLayout.LayoutParams : Info from HierarchyViewer)
            LinearLayout.LayoutParams txvPars = (LinearLayout.LayoutParams) titleTextView.getLayoutParams();
            txvPars.gravity = Gravity.CENTER;
            txvPars.width = metrics.widthPixels - activity.getResources().getDimensionPixelSize(R.dimen.ab_height);
            titleTextView.setLayoutParams(txvPars);
            titleTextView.setGravity(Gravity.CENTER);
            titleTextView.setTypeface(font);
        }

        return titleTextView;
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in setId methods.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

}