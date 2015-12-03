package com.byoutline.secretsauce.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val newType: Typeface) : MetricAffectingSpan() {

    override fun updateDrawState(drawState: TextPaint) {
        apply(drawState)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint)
    }

    private fun apply(paint: Paint) {
        val oldIntrinsicAtrs = paint.typeface?.style ?: 0

        val fake = oldIntrinsicAtrs and newType.style.inv()
        if ((fake and Typeface.BOLD) != 0) {
            paint.isFakeBoldText = true
        }

        if ((fake and Typeface.ITALIC) != 0) {
            paint.textSkewX = -0.25f
        }

        paint.setTypeface(newType)
    }
}