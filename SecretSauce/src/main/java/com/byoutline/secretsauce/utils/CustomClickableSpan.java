package com.byoutline.secretsauce.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class CustomClickableSpan extends ClickableSpan {
    @NonNull
    private final CustomClickableSpan.TextSpanClickedListener listener;

    public CustomClickableSpan(@Nullable TextSpanClickedListener listener) {
        if (listener == null) {
            listener = new StubListener();
        }
        this.listener = listener;
    }

    @Override
    public void onClick(View widget) {
        if (!(widget instanceof TextView)) {
            return;
        }
        TextView tv = (TextView) widget;
        if (tv.getText() instanceof Spanned) {
            Spanned s = (Spanned) tv.getText();
            int start = s.getSpanStart(this);
            int end = s.getSpanEnd(this);
            String clickedText = s.subSequence(start, end).toString();
            listener.onTextClicked(clickedText);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    public interface TextSpanClickedListener {
        void onTextClicked(String text);
    }

    private static class StubListener implements TextSpanClickedListener {

        @Override
        public void onTextClicked(String text) {
        }
    }
}