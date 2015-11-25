package com.byoutline.secretsauce;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import com.byoutline.secretsauce.di.AppComponentInterface;
import com.byoutline.secretsauce.utils.LogUtils;
import com.byoutline.secretsauce.utils.SharedPrefsLoaderAsyncTask;
import com.squareup.otto.Bus;

import javax.annotation.Nonnull;
import javax.inject.Provider;


/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public abstract class BaseApp extends Application {

    private static String TAG = LogUtils.makeLogTag(BaseApp.class);

    private SharedPrefsLoaderAsyncTask sharedPrefsLoader;

    protected abstract void initComponents();

    @Nonnull
    protected abstract AppComponentInterface createAppComponent();

    protected abstract boolean isDebug();

    protected abstract int getContainerId();

    @Nonnull
    protected abstract String getDefaultFontName();

    protected Typeface getActionBarFont() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
        AppComponentInterface component = createAppComponent();
        initByOutlineLibs(component.getBus(), component.getSessionIdProvider());
        sharedPrefsLoader = new SharedPrefsLoaderAsyncTask(this);
        sharedPrefsLoader.execute();
    }

    /**
     * Initialize libs created by Outline.
     */
    private void initByOutlineLibs(Bus bus, Provider<String> sessionIdProvider) {
        com.byoutline.secretsauce.Settings.set(getApplicationContext(), isDebug(), getContainerId(), bus, getDefaultFontName(), getActionBarFont());
        com.byoutline.ottocachedfield.OttoCachedField.init(sessionIdProvider, bus);
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
