package com.byoutline.secretsauce.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.byoutline.cachedfield.CachedField;
import com.byoutline.cachedfield.CachedFieldWithArg;
import com.byoutline.cachedfield.cachedendpoint.CachedEndpointWithArg;
import com.byoutline.cachedfield.utils.CachedFieldsIdleListener;
import com.byoutline.cachedfield.utils.CachedFieldsListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link FrameLayout} that display progress over content with {@link ProgressBar}. Elements
 * are disabled when progress bar is visible.
 * <p>
 * Additionally you can register all your {@link CachedField}/{@link CachedFieldWithArg}/{@link CachedEndpointWithArg}
 * and progress will be shown and hidden automatically.
 * </p>
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class WaitLayout extends FrameLayout implements CachedFieldsIdleListener {
    private static final int BASE_PROGRESSBAR_SIZE = 76;
    private CachedFieldsListener listener;

    View loadingBar;

    public WaitLayout(Context context) {
        super(context);
        init(context);
    }

    public WaitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaitLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
    }

    public void showLoadingBar() {
        setLoadingBarVisibleAsync(true);
    }

    public void hideLoadingBar() {
        setLoadingBarVisibleAsync(false);
    }

    public synchronized void setLoadingBarVisibleAsync(final boolean visible) {
        // Although usually we are in UI thread, post ensures that view is fully constructed.
        this.post(new Runnable() {
            @Override
            public void run() {
                // ensure that we added loading bar
                if (loadingBar == null) {
                    addLoadingBarToView();
                } else {
                    loadingBar.setLayoutParams(getProgressBarLayoutParams());
                }
                showView(loadingBar, visible);
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).setEnabled(!visible);
                }
            }
        });
    }

    private void addLoadingBarToView() {
        loadingBar = new ProgressBar(new ContextThemeWrapper(getContext(), R.style.WaitLayoutProgressBar), null, R.style.WaitLayoutProgressBar);
        LayoutParams lp = getProgressBarLayoutParams();
        addView(loadingBar, lp);
    }

    private LayoutParams getProgressBarLayoutParams() {
        int max = Math.round(convertDpToPixel(BASE_PROGRESSBAR_SIZE, getContext()));
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int smallerDimension = width < height ? width : height;
        if (smallerDimension > max) {
            int padding = (smallerDimension - max) / 2;
            loadingBar.setPadding(padding, padding, padding, padding);
        }
        LayoutParams lp = new LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;
        return lp;
    }


    @SuppressWarnings("unchecked")
    public void stopTrackingProgress() {
        CachedFieldsListener oldL = listener;
        if (oldL != null) {
            oldL.unregisterFromFields();
        }
    }

    public void showProgressOf(CachedField... newFields) {
        showProgressOf(Arrays.asList(newFields), Collections.<CachedFieldWithArg>emptyList(), Collections.<CachedEndpointWithArg>emptyList());
    }

    public void showProgressOf(CachedFieldWithArg... newFields) {
        showProgressOf(Collections.<CachedField>emptyList(), Arrays.asList(newFields), Collections.<CachedEndpointWithArg>emptyList());
    }

    public void showProgressOf(CachedEndpointWithArg... newEndpoints) {
        showProgressOf(Collections.<CachedField>emptyList(), Collections.<CachedFieldWithArg>emptyList(), Arrays.asList(newEndpoints));
    }

    @SuppressWarnings("unchecked")
    public synchronized void showProgressOf(List<CachedField> newFieldsWithoutArgs,
                                            List<CachedFieldWithArg> newFields,
                                            List<CachedEndpointWithArg> newEndpoints) {
        stopTrackingProgress();
        CachedFieldsListener newL = new CachedFieldsListener(newFieldsWithoutArgs, newFields, newEndpoints);
        listener = newL;
        newL.setListener(this);
        newL.startTrackingFields();
    }

    @Override
    public void onFieldsStateChange(boolean currentlyLoading) {
        setLoadingBarVisibleAsync(listener.getCurrentState());
    }

    // Methods copied over to avoid dependency on main Secret Sauce
    private static void showView(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}