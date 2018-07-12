package com.byoutline.secretsauce.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.byoutline.secretsauce.R;


public class CheckableCustomFontTextView extends CompoundButton {
    private static final int DEFAULT_ANGLE = 0;
    private int mTextAngle = DEFAULT_ANGLE;

    public CheckableCustomFontTextView(Context context) {
        super(context);
    }

    public CheckableCustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontAngle(context, attrs);
    }

    public CheckableCustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontAngle(context, attrs);
    }

    private void setFontAngle(Context ctx, AttributeSet attrs) {
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG |
                Paint.ANTI_ALIAS_FLAG);
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        mTextAngle = a.getInt(R.styleable.CustomFontTextView_textAngle, DEFAULT_ANGLE);
        a.recycle();
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