package com.byoutline.secretsauce.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.utils.FontCache;


public class CustomFontTextView extends AppCompatTextView {
    private static final int DEFAULT_ANGLE = 0;
    private int mTextAngle = DEFAULT_ANGLE;

    public CustomFontTextView(Context context) {
        super(context);
        setCustomFont(context, Settings.DEFAULT_FONT_NAME);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String customFont = a.getString(R.styleable.CustomFontTextView_customFont);
        mTextAngle = a.getInt(R.styleable.CustomFontTextView_textAngle, DEFAULT_ANGLE);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public void setCustomFont(Context ctx, String font) {
        if (isInEditMode()) {
            return;
        }

        if (font == null || TextUtils.isEmpty(font)) {
            return;
        }
        Typeface tf = FontCache.get(font, ctx);
        if (tf != null) {
            setTypeface(tf);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //now we change the matrix
        //We need to rotate around the center of our text
        //Otherwise it rotates around the origin, and that's bad.


        boolean changeAngle = mTextAngle != DEFAULT_ANGLE;
        if (changeAngle) {
            float py = this.getHeight() / 2.0f;
            float px = this.getWidth() / 2.0f;
            canvas.rotate(mTextAngle, px, py);
        }

        //draw the text with the matrix applied.
        super.onDraw(canvas);

        //restore the old matrix.
        if (changeAngle) {
            canvas.restore();
        }
    }
}
