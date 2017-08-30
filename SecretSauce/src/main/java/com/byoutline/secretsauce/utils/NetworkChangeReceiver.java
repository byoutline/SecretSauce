package com.byoutline.secretsauce.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.events.InternetStateChangedEvent;
import com.squareup.otto.Produce;

/**
 * Transforms Android broadcasts into {@link com.byoutline.secretsauce.events.InternetStateChangedEvent}s.
 * Remember to call {@link #onResume(android.content.Context)} and {@link #onPause(android.content.Context)}.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private final ConnectivityManager connectivityManager;

    public NetworkChangeReceiver(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Settings.INSTANCE.getBUS().post(produceInternetState());
    }

    public boolean connected() {
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
    }

    @Produce
    public InternetStateChangedEvent produceInternetState() {
        return new InternetStateChangedEvent(connected());
    }

    public void onResume(Context context) {
        context.registerReceiver(this,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));
        Settings.INSTANCE.getBUS().register(this);
    }

    public void onPause(Context context) {
        Settings.INSTANCE.getBUS().unregister(this);
        context.unregisterReceiver(this);
    }
}
