package com.byoutline.secretsauce.views;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

public class CheckableDelegate {
    private final ViewGroup parent;
    private boolean checked;
    private OnCheckedChangeListener onCheckedChangeListener;
    static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public CheckableDelegate(ViewGroup parent) {
        this.parent = parent;
    }

    /**************************/
    /**      Checkable       **/
    /**************************/

    public void toggle() {
        setChecked(!checked);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            parent.refreshDrawableState();
            setCheckedRecursive(parent, checked);
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(null, checked);
            }
        }
    }

    void setCheckedRecursive(ViewGroup parent, boolean checked) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = parent.getChildAt(i);
            if (v instanceof Checkable) {
                ((Checkable) v).setChecked(checked);
            }

            if (v instanceof ViewGroup) {
                setCheckedRecursive((ViewGroup) v, checked);
            }
        }
    }

    public void setOnCheckedChangeListener(CheckableDelegate.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * Interface definition for a callback to be invoked when the checked state
     * of a compound button changed.
     */
    public interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param layout    The compound button view whose state has changed.
         * @param isChecked The new checked state of buttonView.
         */
        void onCheckedChanged(CheckableLinearLayout layout, boolean isChecked);
    }

    /**************************/
    /**   State persistency  **/
    /**
     * **********************
     */

    static class SavedState extends View.BaseSavedState {
        boolean checked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            checked = (Boolean) in.readValue(null);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(checked);
        }

        @Override
        public String toString() {
            return "CheckableDelegate.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " checked=" + checked + "}";
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}