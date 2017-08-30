package com.byoutline.secretsauce;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.byoutline.ottocachedfield.OttoCachedField;
import com.byoutline.secretsauce.di.AppComponentInterface;
import com.byoutline.secretsauce.utils.LogUtils;
import com.byoutline.secretsauce.utils.SharedPrefsLoaderAsyncTask;
import com.squareup.otto.Bus;


/**
 * Application that loads shared preferences asynchronously, and provides
 * alternative interface for setting default libraries value.
 *
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public abstract class BaseApp extends Application {

    private static String TAG = LogUtils.makeLogTag(BaseApp.class);

    private SharedPrefsLoaderAsyncTask sharedPrefsLoader;

    protected abstract boolean isDebug();

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefsLoader = new SharedPrefsLoaderAsyncTask(this);
        sharedPrefsLoader.execute();
    }

    /**
     * Sets default values used by libraries.
     */
    public void init(AppComponentInterface component) {
        Bus bus = component.getBus();
        Settings.set(getApplicationContext(), isDebug(),
                component.getContainerId(), bus,
                component.getDefaultFontName());
        OttoCachedField.init(component.getSessionIdProvider(), bus);
    }

    public SharedPreferences getSharedPrefs() {
        try {
            return sharedPrefsLoader.get();
        } catch (Exception e) {
            LogUtils.LOGE(TAG, "Failed to load shared prefs async", e);
            return loadSharedPrefsSync();
        }
    }

    public SharedPreferences loadSharedPrefsSync() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}
