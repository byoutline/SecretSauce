package com.byoutline.secretsauce.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class CheckableFrameLayout extends FrameLayout implements Checkable {

    private final CheckableDelegate delegate = new CheckableDelegate(this);


    public CheckableFrameLayout(Context context) {
        this(context, null);
        init();
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setClickable(true);
    }

    /**********************/
    /**   Handle clicks  **/
    /**********************/

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    /**************************/
    /**      Checkable       **/
    /**************************/

    public void toggle() {
        delegate.toggle();
    }

    public boolean isChecked() {
        return delegate.isChecked();
    }

    public void setChecked(boolean checked) {
        delegate.setChecked(checked);
    }

    /**************************/
    /**   Drawable States    **/
    /**
     * **********************
     */

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckableDelegate.CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        Drawable drawable = getBackground();
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
            invalidate();
        }
    }

    public void setOnCheckedChangeListener(CheckableDelegate.OnCheckedChangeListener onCheckedChangeListener) {
        delegate.setOnCheckedChangeListener(onCheckedChangeListener);
    }


    /**************************/
    /**   State persistency  **/
    /**
     * **********************
     */


    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        CheckableDelegate.SavedState ss = new CheckableDelegate.SavedState(superState);

        ss.checked = isChecked();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        CheckableDelegate.SavedState ss = (CheckableDelegate.SavedState) state;

        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked);
        requestLayout();
    }
}