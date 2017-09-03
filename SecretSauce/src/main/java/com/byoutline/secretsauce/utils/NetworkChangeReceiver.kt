package com.byoutline.secretsauce.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.databinding.ObservableBoolean
import android.net.ConnectivityManager
import android.os.Build
import android.support.v4.content.ContextCompat
import com.byoutline.secretsauce.Settings

/**
 * Transforms Android broadcasts into [android.databinding.ObservableBoolean].

 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
class NetworkChangeReceiver
(private val connectivityManager: ConnectivityManager,
 val connectedOrConnecting: ObservableBoolean = ObservableBoolean()) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        connectedOrConnecting.set(connected())
    }

    @SuppressLint("MissingPermission")
    fun connected(): Boolean {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(Settings.CONTEXT!!, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            LogUtils.LOGW(TAG, "No permission to read connection state, returning connected=true")
            return true
        }
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
    }

    fun register(context: Context) {
        context.registerReceiver(this,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
    }

    companion object {
        private val TAG = LogUtils.internalMakeLogTag(NetworkChangeReceiver::class.java)
    }
}
