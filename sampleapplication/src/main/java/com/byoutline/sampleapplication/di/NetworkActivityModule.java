package com.byoutline.sampleapplication.di;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.net.ConnectivityManager;

import com.byoutline.secretsauce.utils.NetworkChangeReceiver;

import dagger.Module;
import dagger.Provides;

/**
 * Creates instances needed by activities and fragments.
 */
@Module
public class NetworkActivityModule {

    private final Application app;

    public NetworkActivityModule(Application app) {
        this.app = app;
    }

    @Provides
    ConnectivityManager providesConnectivityManager() {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    NetworkChangeReceiver providesNetworkChangeReceiver(ConnectivityManager connectivityManager) {
        return new NetworkChangeReceiver(connectivityManager, new ObservableBoolean());
    }
}