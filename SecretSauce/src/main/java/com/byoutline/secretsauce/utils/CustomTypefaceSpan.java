package com.byoutline.secretsauce.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomTypefaceSpan extends MetricAffectingSpan {
    public static final float OBLIQUE_SKEW = -0.25f;
    @NonNull
    private final Typeface newType;

    public CustomTypefaceSpan(@NonNull Typeface newType) {
        this.newType = newType;
    }

    @Override
    public void updateDrawState(TextPaint drawState) {
        apply(drawState);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        apply(paint);
    }

    private void apply(Paint paint) {
        final int fakeStyle = isFakeStyle(paint);
        boolean isFakeBold = (fakeStyle & Typeface.BOLD) != 0;
        boolean isFakeItalic = (fakeStyle & Typeface.ITALIC) != 0;

        if (isFakeBold) {
            paint.setFakeBoldText(true);
        }
        if (isFakeItalic) {
            paint.setTextSkewX(OBLIQUE_SKEW);
        }

        paint.setTypeface(newType);
    }

    private int isFakeStyle(Paint paint) {
        Typeface oldTf = paint.getTypeface();
        final int oldStyle = oldTf == null ? 0 : oldTf.getStyle();

        return oldStyle & ~newType.getStyle();
    }
}