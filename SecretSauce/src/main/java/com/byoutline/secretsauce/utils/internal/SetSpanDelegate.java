package com.byoutline.secretsauce.utils.internal;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * @author Sebastian Kacprzak
 */
public class SetSpanDelegate {
    private final SpannableStringBuilder wholeStyleText;
    private final int textStartPos;
    private final int textEndPos;

    protected SetSpanDelegate(SpannableStringBuilder wholeStyleText, int textStartPos, int textEndPos) {
        this.wholeStyleText = wholeStyleText;
        this.textStartPos = textStartPos;
        this.textEndPos = textEndPos;
    }


    public void setSpan(Object what) {
        wholeStyleText.setSpan(what, textStartPos, textEndPos, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }
}
