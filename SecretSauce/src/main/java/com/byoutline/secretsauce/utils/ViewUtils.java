package com.byoutline.secretsauce.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.byoutline.secretsauce.utils.internal.SpanStyler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ViewUtils {
    public static void setStyledMsg(TextView styledTv, String source, String stylingText, CustomTypefaceSpan customSpan, ForegroundColorSpan foregroundDarkColor) {
        SpanStyler.setStyledMsg(styledTv, source, stylingText, customSpan, foregroundDarkColor);
    }

    public static void setStyledMsg(TextView styledTv, String source, List<String> stylingTexts, List<CustomTypefaceSpan> customSpans, List<ForegroundColorSpan> foregroundDarkColors) {
        SpanStyler.setStyledMsg(styledTv, source, stylingTexts, customSpans, foregroundDarkColors);
    }

    public static SpannableStringBuilder getStyledText(String source, List<String> stylingTexts,
                                                       Map<String, CustomTypefaceSpan> typeFaceSpan,
                                                       Map<String, ForegroundColorSpan> textSpanColor,
                                                       Map<String, CustomClickableSpan> clickableSpans,
                                                       Map<String, ImageSpan> iconsSpans,
                                                       Map<String, UnderlineSpan> underlineSpans) {
        return SpanStyler.getStyledText(source, stylingTexts, typeFaceSpan, textSpanColor, clickableSpans, iconsSpans, underlineSpans);
    }

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

    @SuppressLint("PackageManagerGetSignatures")
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