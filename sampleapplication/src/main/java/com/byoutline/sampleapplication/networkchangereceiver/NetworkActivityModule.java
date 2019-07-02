package com.byoutline.sampleapplication.networkchangereceiver;

import android.app.Application;
import android.content.Context;
import androidx.databinding.ObservableBoolean;
import android.net.ConnectivityManager;

import com.byoutline.secretsauce.utils.NetworkChangeReceiver;
import com.byoutline.secretsauce.utils.NetworkChangeViewModel;

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

    @Provides
    NetworkChangeViewModel providesNetworkChangeViewModel(NetworkChangeReceiver networkChangeReceiver) {
        return new NetworkChangeViewModel(networkChangeReceiver);
    }
}