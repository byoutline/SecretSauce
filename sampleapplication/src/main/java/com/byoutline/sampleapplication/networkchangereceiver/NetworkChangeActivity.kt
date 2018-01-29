package com.byoutline.sampleapplication.networkchangereceiver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.NetworkActivityBinding
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.lazyViewModelWithAutoLifecycle
import com.byoutline.secretsauce.utils.NetworkChangeViewModel

/**
 * Remember about permission (ACCESS_NETWORK_STATE)
 * Disable or enable internet connection to see results
 */
class NetworkChangeActivity : AppCompatActivity() {

    private val viewModel by lazyViewModelWithAutoLifecycle(NetworkChangeViewModel::class)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = bindContentView<NetworkActivityBinding>(R.layout.network_activity)
        binding.connected = viewModel.connectedOrConnecting
    }
}
