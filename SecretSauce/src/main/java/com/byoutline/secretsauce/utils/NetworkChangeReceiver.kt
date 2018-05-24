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
import com.byoutline.secretsauce.lifecycle.AttachableViewModel

/**
 * Transforms Android broadcasts into [android.databinding.ObservableBoolean].

 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
class NetworkChangeReceiver
(
    private val connectivityManager: ConnectivityManager,
    val connectedOrConnecting: ObservableBoolean = ObservableBoolean()
) : BroadcastReceiver() {
    private var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        connectedOrConnecting.set(connected())
    }

    @SuppressLint("MissingPermission")
    fun connected(): Boolean {
        val ctx = context ?: return true
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED) {
            LogUtils.LOGW(TAG, "No permission to read connection state, returning connected=true")
            return true
        }
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
    }

    fun register(context: Context) {
        this.context = context
        context.registerReceiver(
            this,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
        this.context = null
    }

    companion object {
        private val TAG = LogUtils.internalMakeLogTag(NetworkChangeReceiver::class.java)
    }
}

class NetworkChangeViewModel(private val receiver: NetworkChangeReceiver) : AttachableViewModel<Context>() {
    val connectedOrConnecting = receiver.connectedOrConnecting

    override fun onAttach(view: Context) {
        receiver.register(view)
        super.onAttach(view)
        registerDetachAction { receiver.unregister(view) }
    }
}