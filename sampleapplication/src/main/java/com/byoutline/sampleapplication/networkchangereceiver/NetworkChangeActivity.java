package com.byoutline.sampleapplication.networkchangereceiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.byoutline.sampleapplication.R;
import com.byoutline.sampleapplication.SampleApp;
import com.byoutline.secretsauce.events.InternetStateChangedEvent;
import com.byoutline.secretsauce.utils.NetworkChangeReceiver;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by michalp on 12.04.16.
 */
public class NetworkChangeActivity extends AppCompatActivity {

    /**
     * remember about permission (ACCESS_NETWORK_STATE)
     * Bus is needed to receive event
     * <p/>
     * disable or enable internet connection to see results
     */
    @Inject
    NetworkChangeReceiver networkChangeReceiver;

    @Inject
    Bus bus;

    @BindView(R.id.networkTv)
    TextView networkTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_activity);
        SampleApp.component.inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver.onResume(this);
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        networkChangeReceiver.onPause(this);
        super.onPause();
    }

    @Subscribe
    public void onInternetConnectionChanged(InternetStateChangedEvent event) {
        //your code
        if (event.isOrWillBeEnabled) {
            networkTv.setText(R.string.network_change_receiver_network_available);
        } else {
            networkTv.setText(R.string.network_change_receiver_network_not_available);
        }
    }
}
