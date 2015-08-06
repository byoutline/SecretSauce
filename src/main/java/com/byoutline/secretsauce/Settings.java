package com.byoutline.secretsauce;

import android.content.Context;
import android.graphics.Typeface;
import android.view.inputmethod.InputMethodManager;
import com.squareup.otto.Bus;

import java.text.SimpleDateFormat;

/**
 * Group of static variables that are required by SecretSauce classes.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class Settings {
    public static Context CONTEXT;
    public static boolean DEBUG;
    public static int CONTAINER_VIEW_ID;
    public static String DEFAULT_FONT_NAME;
    @Deprecated
    public static Typeface ACTIONBAR_FONT_NAME;
    public static InputMethodManager INPUT_MANAGER;
    public static Bus BUS;
    private static String DATE_FORMAT_STRING = "dd/MM/yyyy";
    public static int WAIT_DIALOG_LAYOUT = R.layout.fragment_wait;
    public static int STYLE_DIALOG_FULL_SCREEN = R.style.dialog_full_screen;
    public static String LOG_PREFIX = "SecretS";

    private Settings() {
    }

    /**
     * Init SecretSauce library settings. Please call only once for application, because
     * {@link com.byoutline.secretsauce.models.WaitDialogModel} is created during this call
     * and should be treated as singleton.
     */
    public static void set(Context context, boolean debug, int containerViewId, Bus bus, String defaultFontName, Typeface actionbarFontName) {
        CONTEXT = context;
        DEBUG = debug;
        CONTAINER_VIEW_ID = containerViewId;
        BUS = bus;
        DEFAULT_FONT_NAME = defaultFontName;
        ACTIONBAR_FONT_NAME = actionbarFontName;
        INPUT_MANAGER = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.byoutline.secretsauce.utils.JdkBasedTimeZoneProvider");
    }

    public static SimpleDateFormat setDisplayDateFormat(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        DATE_FORMAT_STRING = dateFormat;
        return format;
    }

    public static SimpleDateFormat getDisplayDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT_STRING);
    }
}
