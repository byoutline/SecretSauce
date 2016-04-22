package com.byoutline.sampleapplication.di;

import android.content.Context;
import android.net.ConnectivityManager;

import com.byoutline.secretsauce.BaseApp;
import com.byoutline.secretsauce.utils.NetworkChangeReceiver;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Creates instances needed by activities and fragments.
 */
@Module
public class GlobalModule {

    private final BaseApp app;
    private final Bus bus;

    public GlobalModule(BaseApp app, Bus bus) {
        this.app = app;
        this.bus = bus;
    }

    @Provides
    @GlobalScope
    Bus provideBus() {
        return bus;
    }

    @Provides
    @GlobalScope
    public ConnectivityManager providesConnectivityManager() {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @GlobalScope
    NetworkChangeReceiver providesNetworkChangeReceiver(ConnectivityManager connectivityManager) {
        return new NetworkChangeReceiver(connectivityManager);
    }
}