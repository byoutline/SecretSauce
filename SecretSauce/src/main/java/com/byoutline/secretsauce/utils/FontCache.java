package com.byoutline.secretsauce.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.byoutline.secretsauce.Settings;

import java.util.Hashtable;

public class FontCache {
    private static final String TAG = LogUtils.internalMakeLogTag(FontCache.class);
    private static final Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(String name, Context context) {
        if (TextUtils.isEmpty(name)) {
            name = Settings.DEFAULT_FONT_NAME;
        }

        Typeface tf = fontCache.get(name);
        if (tf == null) {
            tf = createFontOrDefault(name, context);
            if (tf != Typeface.DEFAULT) {
                // Default font is already cached, there is no need to cache it again.
                fontCache.put(name, tf);
            }
        }
        return tf;
    }

    private static Typeface createFontOrDefault(String name, Context context) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
        } catch (RuntimeException ex) {
            LogUtils.LOGE(TAG, ex);
        }
        if (tf == null) {
            LogUtils.LOGW(TAG, "Could not find the font with name: " + name);
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + Settings.DEFAULT_FONT_NAME);
        }
        if (tf == null) {
            LogUtils.LOGW(TAG, "Could not find default project font with name: " + name);
            tf = Typeface.DEFAULT;
        }
        return tf;
    }
}
