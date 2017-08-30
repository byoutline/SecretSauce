package com.byoutline.secretsauce;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.view.inputmethod.InputMethodManager;
import com.squareup.otto.Bus;

/**
 * Group of static variables that are required by SecretSauce classes.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class Settings {
    public static Context CONTEXT;
    public static boolean DEBUG;
    @IdRes
    public static int CONTAINER_VIEW_ID;
    @Deprecated
    public static String DEFAULT_FONT_NAME;
    public static InputMethodManager INPUT_MANAGER;
    public static Bus BUS;
    public static int WAIT_DIALOG_LAYOUT = R.layout.fragment_wait;
    public static int STYLE_DIALOG_FULL_SCREEN = R.style.dialog_full_screen;
    public static String LOG_PREFIX = "SecretS";

    private Settings() {
    }

    /**
     * Init SecretSauce library settings.
     */
    public static void set(Context context, boolean debug, @IdRes int containerViewId, Bus bus) {
        set(context, debug, containerViewId, bus, "");
    }

    /**
     * @deprecated actionbarFontName is no longer supported.
     */
    @Deprecated
    public static void set(Context context, boolean debug, @IdRes int containerViewId, Bus bus, String defaultFontName, Typeface actionbarFontName) {
        set(context, debug, containerViewId, bus, defaultFontName);
    }

    public static void set(Context context, boolean debug, @IdRes int containerViewId, Bus bus, String defaultFontName) {
        CONTEXT = context.getApplicationContext();
        DEBUG = debug;
        CONTAINER_VIEW_ID = containerViewId;
        BUS = bus;
        DEFAULT_FONT_NAME = defaultFontName;
        INPUT_MANAGER = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider");
    }
}
