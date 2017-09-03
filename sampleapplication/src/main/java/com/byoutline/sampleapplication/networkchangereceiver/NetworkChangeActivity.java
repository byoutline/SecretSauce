package com.byoutline.sampleapplication.networkchangereceiver;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.byoutline.sampleapplication.R;
import com.byoutline.sampleapplication.SampleApp;
import com.byoutline.sampleapplication.databinding.NetworkActivityBinding;
import com.byoutline.secretsauce.utils.NetworkChangeReceiver;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by michalp on 12.04.16.
 */
public class NetworkChangeActivity extends AppCompatActivity {

    /**
     * remember about permission (ACCESS_NETWORK_STATE)
     * <p/>
     * disable or enable internet connection to see results
     */
    @Inject
    NetworkChangeReceiver networkChangeReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SampleApp.Companion.getComponent().inject(this);
        NetworkActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.network_activity);
        binding.setConnected(networkChangeReceiver.getConnectedOrConnecting());
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver.register(this);
    }

    @Override
    protected void onPause() {
        networkChangeReceiver.unregister(this);
        super.onPause();
    }
}
