package com.byoutline.secretsauce.views;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class LinkTextView extends AppCompatTextView {

    private LinkClickListener linkListener = new StubLinkListener();

    public LinkTextView(Context context) {
        super(context);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    void makeLinkClickable(SpannableStringBuilder strBuilder, URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        final String url = span.getURL();
        ClickableSpan clickable = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                linkListener.onClick(url);
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    SpannableStringBuilder getSpannableHtml(String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        return strBuilder;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
        } else {
            SpannableStringBuilder spannableHtml = getSpannableHtml(text.toString());
            super.setText(spannableHtml, BufferType.SPANNABLE);
        }
    }

    public void setOnLinkClickListener(LinkClickListener l) {
        if (l == null) {
            l = new StubLinkListener();
        }
        linkListener = l;
    }

    private static class StubLinkListener implements LinkClickListener {

        @Override
        public void onClick(String url) {
        }
    }
}
