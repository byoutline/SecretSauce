package com.byoutline.sampleapplication.networkchangereceiver

import android.os.Bundle
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.NetworkActivityBinding
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.lazyViewModelWithAutoLifecycle
import com.byoutline.secretsauce.utils.NetworkChangeViewModel

/**
 * Remember about permission (ACCESS_NETWORK_STATE)
 * Disable or enable internet connection to see results
 */
class NetworkChangeActivity : ClassNameAsToolbarTitleActivity() {

    private val viewModel by lazyViewModelWithAutoLifecycle(NetworkChangeViewModel::class)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = bindContentView<NetworkActivityBinding>(R.layout.network_activity)
        binding.connected = viewModel.connectedOrConnecting
    }
}
