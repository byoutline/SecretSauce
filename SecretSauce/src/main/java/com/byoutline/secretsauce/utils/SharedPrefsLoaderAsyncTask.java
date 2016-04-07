package com.byoutline.secretsauce.utils;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.byoutline.secretsauce.BaseApp;


/**
 * {@link AsyncTask} that loads {@link SharedPreferences} to memory in background.
 * Created by Sebastian Kacprzak <sebastian.kacprzak at byoutline.com> on 27.03.15.
 */
public class SharedPrefsLoaderAsyncTask extends AsyncTask<Void, Void, SharedPreferences> {
    private BaseApp baseApp;

    public SharedPrefsLoaderAsyncTask(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override
    protected SharedPreferences doInBackground(Void... voids) {
        return baseApp.loadSharedPrefsSync();
    }
}
