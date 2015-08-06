package com.byoutline.secretsauce.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.utils.FontCache;

public class CustomFontEditText extends AppCompatEditText {

    public CustomFontEditText(Context context) {
        super(context);
        setCustomFont(context, Settings.DEFAULT_FONT_NAME);
    }


    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String customFont = a.getString(R.styleable.CustomFontTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public void setCustomFont(Context ctx, String font) {
        if (font == null || TextUtils.isEmpty(font)) {
            return;
        }
        Typeface tf = FontCache.get(font, ctx);
        if (tf != null) {
            setTypeface(tf);
        }
    }

}
