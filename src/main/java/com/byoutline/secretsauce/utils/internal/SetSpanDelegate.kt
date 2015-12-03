package com.byoutline.secretsauce.utils.internal

import android.text.SpannableStringBuilder
import android.text.Spanned

/**
 * @author Sebastian Kacprzak
 */
class SetSpanDelegate(private val wholeStyleText: SpannableStringBuilder, private val textStartPos: Int,
                      private val textEndPos: Int) {
    fun setSpan(what: Object) {
        wholeStyleText.setSpan(what, textStartPos, textEndPos, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
    }
}
