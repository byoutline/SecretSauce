package com.byoutline.sampleapplication.di;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.net.ConnectivityManager;
import com.byoutline.ottoeventcallback.PostFromAnyThreadBus;
import com.byoutline.sampleapplication.draweractivity.ToolbarViewModel;
import com.byoutline.secretsauce.utils.NetworkChangeReceiver;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Creates instances needed by activities and fragments.
 */
@Module
public class GlobalModule {

    private final Application app;

    public GlobalModule(Application app) {
        this.app = app;
    }

    @Provides
    @GlobalScope
    Bus provideBus() {
        return new PostFromAnyThreadBus();
    }

    @Provides
    @Reusable
    ConnectivityManager providesConnectivityManager() {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Reusable
    NetworkChangeReceiver providesNetworkChangeReceiver(ConnectivityManager connectivityManager) {
        return new NetworkChangeReceiver(connectivityManager, new ObservableBoolean());
    }

    @Provides
    @Reusable
    ToolbarViewModel providesToolbarViewModel() {
        return new ToolbarViewModel();
    }
}