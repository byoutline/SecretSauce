package com.byoutline.secretsauce.utils

import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class CustomClickableSpan(private val listener: CustomClickableSpan.TextSpanClickedListener?) : ClickableSpan() {

    interface TextSpanClickedListener {
        fun onTextClicked(text: String)
    }

    override fun onClick(widget: View) {
        if (listener != null && widget is TextView) {
            if (widget.text is Spanned) {
                val s = widget.text as Spanned
                val start = s.getSpanStart(this)
                val end = s.getSpanEnd(this)
                listener.onTextClicked(s.subSequence(start, end).toString())
            }
        }
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}